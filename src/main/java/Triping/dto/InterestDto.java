package Triping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InterestDto {
    private Long id;
    private String description;

    public InterestDto(Long id, String description){
        this.id = id;
        this.description = description;
    }
}
