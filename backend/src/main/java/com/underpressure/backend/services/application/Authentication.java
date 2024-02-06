package com.underpressure.backend.services.application;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.unexpected.AuthenticationFailedException;
import com.underpressure.backend.exceptions.unexpected.InternalServerError;
import com.underpressure.backend.requests.body.AuthenticationRequestBody;
import com.underpressure.backend.responses.OAuthTokenDto;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;

public class Authentication {

    private GoogleService googleService;
    private DatabaseService databaseService;

    private String tokenUri;
    private String redirectUri;
    private String clientId;
    private String clientSecret;
    private String scope = "profile openid";
    private String grantType = "authorization_code";

    private RestTemplate restTemplate = new RestTemplate();

    public Authentication(
            GoogleService googleService,
            DatabaseService databaseService,
            String tokenUri,
            String redirectUri,
            String clientId,
            String clientSecret) {
        this.googleService = googleService;
        this.databaseService = databaseService;
        this.tokenUri = tokenUri;
        this.redirectUri = redirectUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    String handle(AuthenticationRequestBody requestData) {
        String code = requestData.getCode(); // Authorisation code from frontend
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

            return idTokenString;

        }

        return idTokenString;
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
