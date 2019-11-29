package Triping.dto;

import Triping.models.User;
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

    public UserDto(User user){
        this.setId(user.getUserId());
        this.setUsername(user.getSurname());
        this.setEmail(user.getEmail());
        this.setUserImage(user.getUserImage());

        this.setName(user.getName());
        this.setSurname(user.getSurname());
    }
}
