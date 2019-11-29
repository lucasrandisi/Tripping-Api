package Triping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private byte[] userImage;
}
