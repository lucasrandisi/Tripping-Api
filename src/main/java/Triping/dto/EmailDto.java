package Triping.dto;

import Triping.validation.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class EmailDto {

    @ValidEmail
    @NotNull @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String password;
}
