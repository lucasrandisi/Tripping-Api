package Triping.controllers;

import Triping.validation.PasswordMatches;
import Triping.validation.ValidEmail;
import Triping.validation.ValidPassword;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@PasswordMatches
public class UserDto implements Serializable {

    @ValidPassword
    @NotNull @NotEmpty
    private String password;

    @NotNull @NotEmpty
    @SerializedName("password_confirm")
    private String matchingPassword;

    @ValidEmail
    @NotNull @NotEmpty
    private String email;

    @NotNull @NotEmpty
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(final String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [username=").append(username).append(", password=").append(password).append(", matchingPassword=").append(matchingPassword).append(", email=").append(email).append("]");
        return builder.toString();
    }
}
