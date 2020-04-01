package Triping.controllers;

import Triping.models.InvitationToken;
import Triping.models.Group;
import Triping.models.User;
import Triping.services.specifications.IGroupService;
import Triping.services.specifications.IUserService;
import Triping.utils.exceptions.AccessDeniedException;

import Triping.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IUserService userService;

    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getOne(@PathVariable(value = "id") Long id)  throws ResourceNotFoundException {
        Group group = groupService.getOne(id);
        return ResponseEntity.ok().body(group);
    }

    @PostMapping
    public ResponseEntity<Group> createNewTrip(@Valid @RequestBody Group group){
        final Group created = groupService.createNewGroup(group);
        return ResponseEntity.ok().body(group);
    }

    /**
     * Edit the basic information of a group. Requires admin privileges.
     * @param id group to edit
     * @param groupDetails new set of data
     * @return group information
     * @throws ResourceNotFoundException
     */
    @PutMapping("/{id}")
    public ResponseEntity<Group> updateTrip(@PathVariable(value = "id") Long id, @Valid @RequestBody Group groupDetails) throws ResourceNotFoundException {
        final Group updatedGroup = groupService.updateTrip(id, groupDetails);
        return ResponseEntity.ok().body(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteTrip(@PathVariable Long id) throws ResourceNotFoundException {
        groupService.deleteTrip(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    /***
     * Adds a user to the group. Requires admin privileges.
     * @param id
     * @param username of the user to invite
     * @return
     * @throws ResourceNotFoundException
     */
    @PostMapping("/{id}/add")
    public ResponseEntity<?> addContributor(@PathVariable Long id, @RequestParam(name = "username") String username) throws ResourceNotFoundException {
        final User authenticatedUser = this.getAuthenticatedUser();
        final Group group = groupService.getOne(id);
        final User invitee = userService.findUserByUsername(username);

        if(group.hasOwner(authenticatedUser)){
            groupService.addContributorToGroup(group, invitee);
            return new ResponseEntity<>("User invitation is pending on confirmation", HttpStatus.OK);
        }
        else{
            throw new AccessDeniedException("No tienes acceso para realizar esta operacion");
        }
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<?> removeContributor(@PathVariable Long id, @RequestParam(name = "username") String username) throws ResourceNotFoundException {
        final User authenticatedUser = this.getAuthenticatedUser();
        final Group group = groupService.getOne(id);
        final User contributor = userService.findUserByUsername(username);

        if(group.hasOwner(authenticatedUser)){
            groupService.removeContributorFromGroup(group, contributor);
            return new ResponseEntity<>("Contributor removed from trip", HttpStatus.OK);
        }
        else{
            throw new AccessDeniedException("No tienes acceso para realizar esta operacion");
        }
    }

    /***
     * Generates an invitation token for anybody to join the group.
     * See /join endpoint.
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @PostMapping(path="/{id}/invite")
    public ResponseEntity<?> generateInvitationLink(@PathVariable Long id) throws ResourceNotFoundException {
        final User authenticatedUser = this.getAuthenticatedUser();
        final Group group = groupService.getOne(id);

        String token = UUID.randomUUID().toString();
        groupService.createInvitationToken(group, token);

        String confirmationUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/join?token=" + token;
        return ResponseEntity.ok(confirmationUrl);
    }

    @GetMapping(path="/join")
    public ResponseEntity<?> joinGroupViaInvitationLink(@RequestParam String token){

        InvitationToken invitationToken = groupService.getInvitationToken(token);
        if (invitationToken == null) {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
        if(invitationToken.isExpired()){
            return new ResponseEntity<>("Expired token", HttpStatus.BAD_REQUEST);
        }

        Group group = invitationToken.getGroup();
        final User invitee = this.getAuthenticatedUser();
        groupService.addContributorToGroup(group, invitee);

        URI resourceLocation = ServletUriComponentsBuilder.fromPath("/groups/{id}")
                .buildAndExpand(group.getGroupId()).toUri();

        return ResponseEntity.ok(resourceLocation);
    }

    public User getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByUsername(auth.getPrincipal().toString());
    }
}
