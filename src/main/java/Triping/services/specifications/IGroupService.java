package Triping.services.specifications;

import Triping.models.InvitationToken;
import Triping.models.Group;
import Triping.models.User;
import Triping.utils.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IGroupService {
    
    List<Group> findAll();

    Group getOne(Long id) throws ResourceNotFoundException;

    Group createNewGroup(Group group);

    Group updateTrip(Long id, Group groupDetails)  throws ResourceNotFoundException;

    void deleteTrip(Long id)  throws ResourceNotFoundException;

    void addContributorToGroup(Group group, User contributor);

    void removeContributorFromGroup(Group group, User contributor);

    InvitationToken getInvitationToken(String token);

    void createInvitationToken(Group group, String token);
}
