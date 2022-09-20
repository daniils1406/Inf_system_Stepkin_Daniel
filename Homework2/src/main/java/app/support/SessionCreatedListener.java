package app.support;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class SessionCreatedListener implements HttpSessionListener {

    private static final AtomicInteger ACTIVE_SESSIONS = new AtomicInteger();

    public static int getTotalActiveSession() {
        return ACTIVE_SESSIONS.get();
    }

    @Override
    public void sessionCreated(final HttpSessionEvent event) {
        ACTIVE_SESSIONS.incrementAndGet();
    }

    @Override
    public void sessionDestroyed(final HttpSessionEvent event) {
        ACTIVE_SESSIONS.decrementAndGet();
    }
}