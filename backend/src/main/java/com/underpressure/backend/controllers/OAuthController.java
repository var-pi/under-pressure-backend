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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.underpressure.backend.controllers.classes.request.body.AuthenticateBody;
import com.underpressure.backend.controllers.classes.request.data.OAuthTokenResponse;

@RestController
public class OAuthController {

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
    @PostMapping("/authenticate")
    public ResponseEntity<String> Authenticate(@RequestBody AuthenticateBody entity) throws Exception {
        String code = entity.getCode(); // Authorisation code from frontend

        // User granted us some permissions and now we can request an access token from
        // the authorisation/resource server.
        ResponseEntity<String> responseEntity = exchangeAuthCodeForAccessToken(code);

        // Parse the response entity body into the Java object.
        OAuthTokenResponse oAuthTokenResponse = (new ObjectMapper())
                .readValue(responseEntity.getBody(), OAuthTokenResponse.class);

        // Get encoded JWT (OpenID Connect)
        String idTokenString = oAuthTokenResponse.getIdToken();

        // Verify the JWT (ex. not expired) and get the decoded OpenID Connect id.
        String openId = getOpenID(idTokenString);

        System.out.println(openId);

        return new ResponseEntity<String>(idTokenString, HttpStatus.OK);
    }

    private ResponseEntity<String> exchangeAuthCodeForAccessToken(String code) {

        // https://www.javainuse.com/spring/spring-boot-oauth-access-token

        HttpHeaders headers = new HttpHeaders();
        String credentials = clientId + ":" + clientSecret; // ID and secret
        String encodedCredentials = encodeCredentials(credentials); // Ecode base to Base64

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + encodedCredentials); // Add credentials
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

        // The non-credential parameters are in the URL.
        String url = tokenUri
                + "?code=" + code
                + "&redirect_uri=" + redirectUri
                + "&scope=" + scope
                + "&grant_type=" + grantType;

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    private static String encodeCredentials(String credentials) {
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        byte[] encodedBytes = Base64.getEncoder().encode(credentialsBytes);
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    private String getOpenID(String idTokenString) throws Exception {

        // https://developers.google.com/identity/gsi/web/guides/verify-google-id-token

        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            return payload.getSubject(); // Users OpenID
            // By specifying different scope in the frontend much more info can be fetched.
        } else {
            throw new Exception("Invalid ID token.");
        }
    }

}
