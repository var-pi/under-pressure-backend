package com.underpressure.backend.controllers.classes;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;

import com.underpressure.backend.controllers.helpers.Extract;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.exceptions.unexpected.UserVerificationException;

@Import({ Fetch.Google.class, Extract.class })
public class AuthorizedControllerTests<T> extends ControllerTests<T> {

    @SpyBean
    Fetch.Google fetchGoogleMock;

    @BeforeEach
    public void setUp() throws UserVerificationException {
        doReturn("10001").when(fetchGoogleMock).sub(eq("user_1_id_token"), anyString());
        doReturn("10002").when(fetchGoogleMock).sub(eq("user_2_id_token"), anyString());
        doReturn("10004").when(fetchGoogleMock).sub(eq("user_4_id_token"), anyString());
    }

}
