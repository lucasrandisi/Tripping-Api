package Triping.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PasswordDto  implements Serializable {
    //@ValidPassword
    @NotNull
    @NotEmpty
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
