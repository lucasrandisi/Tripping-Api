package Triping.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter @Setter
public class PasswordDto  implements Serializable {

    @NotNull
    @NotEmpty
    private String oldPassword;

    //@ValidPassword
    @NotNull
    @NotEmpty
    private String newPassword;

}
