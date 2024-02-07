package com.underpressure.backend.controllers.data.abstracts;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.underpressure.backend.exceptions.auth.UserVerificationException;
import com.underpressure.backend.services.google.FetchGoogle;
import com.underpressure.backend.services.google.GoogleService;

public abstract class AuthorizedControllerTests<T> extends ControllerTests<T> {

    protected FetchGoogle fetchGoogleSpy;

    @SpyBean
    GoogleService googleServiceSpy;

    @BeforeEach
    public void setUp() throws UserVerificationException {

        fetchGoogleSpy = spy(googleServiceSpy.fetch());
        doReturn(fetchGoogleSpy).when(googleServiceSpy).fetch();

        doReturn("10001").when(fetchGoogleSpy).sub(eq("user_1_id_token"), any());
        doReturn("10002").when(fetchGoogleSpy).sub(eq("user_2_id_token"), any());
        doReturn("10004").when(fetchGoogleSpy).sub(eq("user_4_id_token"), any());

    }

}
