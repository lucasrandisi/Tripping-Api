package Triping.tasks;

import Triping.models.User;
import Triping.models.VerificationToken;
import org.springframework.context.ApplicationEvent;

/**
 * Event that triggers the execution of sending verification token
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String appUrl;
    private User user;
    private VerificationToken verificationToken;

    public OnRegistrationCompleteEvent(User user, String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }

    public OnRegistrationCompleteEvent(User user, String appUrl, VerificationToken verificationToken){
        super(user);
        this.user = user;
        this.appUrl = appUrl;
        this.verificationToken = verificationToken;
    }

    public User getUser() { return user;}

    public void setUser(User user) { this.user = user; }

    public String getAppUrl() { return appUrl; }

    public void setAppUrl(String appUrl) { this.appUrl = appUrl; }

    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
    }
}
