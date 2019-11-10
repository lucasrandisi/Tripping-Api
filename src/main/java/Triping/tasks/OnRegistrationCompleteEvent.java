package Triping.tasks;

import Triping.models.User;
import org.springframework.context.ApplicationEvent;

/**
 * Event that triggers the execution of sending verification token
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String appUrl;
    private User user;

    public OnRegistrationCompleteEvent(User user, String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }

    public User getUser() { return user;}

    public void setUser(User user) { this.user = user; }

    public String getAppUrl() { return appUrl; }

    public void setAppUrl(String appUrl) { this.appUrl = appUrl; }
}
