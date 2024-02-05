package com.underpressure.backend.controllers;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.classes.request.body.AuthenticationBody;
import com.underpressure.backend.controllers.classes.request.data.OAuthTokenResponse;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.If;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.unexpected.AuthenticationFailedException;
import com.underpressure.backend.exceptions.unexpected.InternalServerError;
import com.underpressure.backend.exceptions.unexpected.UserVerificationException;

@RestController
public class AuthenticationController extends PostController<String, AuthenticationBody> {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    private String scope = "openid";
    private String grantType = "authorization_code";

    @CrossOrigin(origins = "*")
    @PostMapping("/auth")
    @Override
    public ResponseEntity<ApiResponse<String>> handle(@RequestBody AuthenticationBody entity) {

        try {
            String code = entity.getCode(); // Authorisation code from frontend
            Validate.code(code);

            // User granted us some permissions and now we can request an access token from
            // the authorisation/resource server.
            OAuthTokenResponse oAuthTokenResponse = exchangeAuthCodeForAccessToken(code);

            // Get encoded JWT (OpenID Connect)
            String idTokenString = oAuthTokenResponse.getIdToken();

            // Verify the JWT (ex. not expired) and get the decoded OpenID Connect id.
            Payload userInfo = getVerifiedUserInfo(idTokenString);

            String googleSub = userInfo.getSubject();
            if (!If.userWithGoogleSubExists(googleSub, jdbcTemplate)) {
                Add.user(userInfo, jdbcTemplate);

                return new ResponseEntity<>(
                        new ApiResponse<>(true, idTokenString, null),
                        HttpStatus.CREATED);
            }

            return new ResponseEntity<>(
                    new ApiResponse<>(true, idTokenString, null),
                    HttpStatus.OK);
        } catch (RequestException e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, e.getMessage()),
                    e.getHttpStatus());
        }
    }

    private OAuthTokenResponse exchangeAuthCodeForAccessToken(String code) throws RequestException {

        // https://www.javainuse.com/spring/spring-boot-oauth-access-token

        HttpHeaders headers = new HttpHeaders();
        String credentials = clientId + ":" + clientSecret; // ID and secret
        String encodedCredentials = encodeCredentials(credentials); // Ecode base to Base64

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + encodedCredentials); // Add credentials
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // The non-credential parameters are in the URL.
        String url = tokenUri
                + "?code=" + code
                + "&redirect_uri=" + redirectUri
                + "&scope=" + scope
                + "&grant_type=" + grantType;

        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.POST, requestEntity, String.class);

        System.out.println(responseEntity.getBody());

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK))
            throw new AuthenticationFailedException();

        // Parse the response entity body into the Java object.
        try {
            return (new ObjectMapper())
                    .readValue(responseEntity.getBody(), OAuthTokenResponse.class);
        } catch (Exception e) {
            throw new InternalServerError();
        }
    }

    private static String encodeCredentials(String credentials) {
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        byte[] encodedBytes = Base64.getEncoder().encode(credentialsBytes);
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    private Payload getVerifiedUserInfo(String idTokenString) throws UserVerificationException {

        // https://developers.google.com/identity/gsi/web/guides/verify-google-id-token

        try {
            HttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                return payload;
                // By specifying different scope in the frontend much more info can be fetched.
            } else {
                throw new Exception("Invalid ID token.");
            }
        } catch (Exception e) {
            throw new UserVerificationException();
        }
    }

}
