package org.softuni.mobilele.model.events;

import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {
    private final String userEmail;
    private final String usernames;

    public UserRegisteredEvent(Object source,
                               String userEmail,
                               String usernames) {
        super(source);
        this.userEmail = userEmail;
        this.usernames = usernames;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUsernames() {
        return usernames;
    }
}
