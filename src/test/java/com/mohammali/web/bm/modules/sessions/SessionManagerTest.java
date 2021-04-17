package com.mohammali.web.bm.modules.sessions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

class SessionManagerTest {

    private final int TIME_OUT_IN_SECONDS = 3;

    private final SessionManager testSubject = new SessionManagerImpl(Duration.ofSeconds(TIME_OUT_IN_SECONDS));

    @Test
    void whenAddSessionCallThenSaveTheSession() {
        var dummyId = "1";
        var session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getId()).thenReturn(dummyId);
        testSubject.addSession(session);

        Assertions.assertNotNull(testSubject.findById(dummyId));
    }

    @Test
    void whenDeleteByIdCallThenDeleteSavedSession() {
        var dummyId = "1";
        var session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getId()).thenReturn(dummyId);
        testSubject.addSession(session);

        Assertions.assertNotNull(testSubject.findById(dummyId));

        testSubject.deleteById(dummyId);
        Assertions.assertNull(testSubject.findById(dummyId));
    }

    @Test
    void whenInvalidateCallThenRemoveAllSavedSession() {
        var dummyIds = Arrays.asList("1", "2", "3");
        for (String dummyId : dummyIds) {
            var session = Mockito.mock(HttpSession.class);
            Mockito.when(session.getId()).thenReturn(dummyId);
            testSubject.addSession(session);
        }

        Assertions.assertEquals(dummyIds.size(), testSubject.invalidate());
    }

    @Test
    void whenSessionTimeoutThenRemoveSavedSession() throws InterruptedException {
        var dummyId = "1";
        var session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getId()).thenReturn(dummyId);
        testSubject.addSession(session);

        TimeUnit.SECONDS.sleep(TIME_OUT_IN_SECONDS + 2);

        Assertions.assertNull(testSubject.findById(dummyId));
    }
}
