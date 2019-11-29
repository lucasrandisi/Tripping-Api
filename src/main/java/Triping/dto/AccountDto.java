package Triping.dto;

import Triping.validation.PasswordMatches;
import Triping.validation.ValidEmail;
import Triping.validation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter @Setter
@PasswordMatches
public class AccountDto implements Serializable {

    //@ValidPassword
    @NotNull @NotEmpty
    private String password;

    @NotNull @NotEmpty
    private String matchingPassword;

    @ValidEmail
    @NotNull @NotEmpty
    private String email;

    @NotNull @NotEmpty
    private String username;

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [username=").append(username).append(", password=").append(password).append(", matchingPassword=").append(matchingPassword).append(", email=").append(email).append("]");
        return builder.toString();
    }
}
