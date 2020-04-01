package Triping.services.implementations;

import Triping.models.InvitationToken;
import Triping.models.Group;
import Triping.models.User;
import Triping.repositories.*;
import Triping.services.specifications.IAccountService;
import Triping.services.specifications.IGroupService;
import Triping.utils.exceptions.NotImplementedException;
import Triping.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupService implements IGroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private InvitationTokenRepository invitationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IAccountService accountService;

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group getOne(Long id) throws ResourceNotFoundException{
        final Group group = groupRepository.getOne(id);
        return group;
    }

    @Override
    public Group createNewGroup(Group group){
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());
        group.setOwner(authenticatedUser);
        return groupRepository.save(group);
    }

    @Override
    public Group updateTrip(Long id, Group groupDetails)  throws ResourceNotFoundException{
        final Group edit = this.getOne(id);
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());

        if(edit.getOwner().equals(authenticatedUser)) {
            edit.setTitle(groupDetails.getTitle());
            edit.setDescription(groupDetails.getDescription());
            return groupRepository.save(edit);
        }
        else{
            throw new ResourceNotFoundException("No tienes acceso para realizar esta operacion");
        }
    }

    @Override
    public void deleteTrip(Long id)  throws ResourceNotFoundException{
        final Group group = this.getOne(id);
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());

        if(group.hasOwner(authenticatedUser)) {
            groupRepository.delete(group);
        }
        else{
            throw new ResourceNotFoundException("No tienes acceso para realizar esta operacion");
        }
    }

    @Override
    public void addContributorToGroup(Group group, User contributor) {
        throw new NotImplementedException();
    }

    @Override
    public void removeContributorFromGroup(Group group, User contributor) {
        throw new NotImplementedException();
    }

    @Override
    public InvitationToken getInvitationToken(String token) {
        return invitationTokenRepository.findByToken(token);
    }

    @Override
    public void createInvitationToken(Group group, String token) {
        InvitationToken verificationToken = new InvitationToken(group, token);
        invitationTokenRepository.save(verificationToken);
    }
}
