package com.underpressure.backend.controllers;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.underpressure.backend.abstracts.PostController;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.unexpected.AuthenticationFailedException;
import com.underpressure.backend.exceptions.unexpected.InternalServerError;
import com.underpressure.backend.requests.body.AuthenticationRequestBody;
import com.underpressure.backend.responses.OAuthTokenDto;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;

@RestController
public class AuthenticationController extends PostController<String, AuthenticationRequestBody> {

    DatabaseService databaseService;
    GoogleService googleService;

    public AuthenticationController(DatabaseService databaseService, GoogleService googleService) {
        this.databaseService = databaseService;
        this.googleService = googleService;
    }

    RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    private String scope = "profile openid";
    private String grantType = "authorization_code";

    @Override
    @PostMapping("/auth")
    public ResponseEntity<String> handle(@RequestBody AuthenticationRequestBody entity) {

        String code = entity.getCode(); // Authorisation code from frontend
        databaseService.validate().code(code);

        // User granted us some permissions and now we can request an access token from
        // the authorisation/resource server.
        OAuthTokenDto oAuthTokenResponse = exchangeAuthCodeForAccessToken(code);

        // Get encoded JWT (OpenID Connect)
        String idTokenString = oAuthTokenResponse.getIdToken();

        // Verify the JWT (ex. not expired) and get the decoded OpenID Connect id.
        Payload userInfo = googleService.fetch().userInfo(idTokenString, clientId);

        String googleSub = userInfo.getSubject();
        if (!databaseService.check().userWithGoogleSubExists(googleSub)) {
            databaseService.add().user(userInfo);

            return new ResponseEntity<>(idTokenString, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(idTokenString, HttpStatus.OK);

    }

    private OAuthTokenDto exchangeAuthCodeForAccessToken(String code) throws RequestException {

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

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK))
            throw new AuthenticationFailedException();

        // Parse the response entity body into the Java object.
        ObjectMapper objectMapper = new ObjectMapper();
        // Convert from 'snake_case' to 'camelCase'.
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        try {
            return objectMapper.readValue(responseEntity.getBody(), OAuthTokenDto.class);
        } catch (Exception e) {
            throw new InternalServerError();
        }
    }

    private String encodeCredentials(String credentials) {
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        byte[] encodedBytes = Base64.getEncoder().encode(credentialsBytes);
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

}
