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
    private byte[] profilePicture;

    public UserDto(User user){
        this.setId(user.getUserId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setProfilePicture(user.getProfilePicture());

        this.setName(user.getName());
        this.setSurname(user.getSurname());
    }
}
