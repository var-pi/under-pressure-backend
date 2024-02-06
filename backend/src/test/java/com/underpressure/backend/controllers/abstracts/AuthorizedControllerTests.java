package com.underpressure.backend.controllers.abstracts;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;

import com.underpressure.backend.exceptions.unexpected.UserVerificationException;
import com.underpressure.backend.services.google.FetchGoogle;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

@Import({
        GoogleService.class,
        UtilityService.class
})
public abstract class AuthorizedControllerTests<T> extends ControllerTests<T> {

    @SpyBean
    GoogleService googleServiceSpy;

    @BeforeEach
    public void setUp() throws UserVerificationException {
        FetchGoogle fetchGoogleSpy = spy(googleServiceSpy.fetch());
        doReturn(fetchGoogleSpy).when(googleServiceSpy).fetch();

        doReturn("10001").when(fetchGoogleSpy).sub(eq("user_1_id_token"), anyString());
        doReturn("10002").when(fetchGoogleSpy).sub(eq("user_2_id_token"), anyString());
        doReturn("10004").when(fetchGoogleSpy).sub(eq("user_4_id_token"), anyString());
    }

}
