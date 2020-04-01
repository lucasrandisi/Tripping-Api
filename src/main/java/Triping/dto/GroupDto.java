package Triping.dto;

import Triping.models.Group;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class GroupDto {
    private Long id;
    private String title;
    private String description;
    private Date departureDate;
    private Date endDate;

    private UserDto owner;

    public GroupDto(Group group) {
        this.setId(group.getGroupId());
        this.setTitle(group.getTitle());
        this.setDescription(group.getDescription());
        this.setDepartureDate(group.getCreationDate());

        UserDto owner = new UserDto(group.getOwner());
        this.setOwner(owner);

    }
}
