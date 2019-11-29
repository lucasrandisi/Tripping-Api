package Triping.services.implementations;

import Triping.dto.AccountDto;
import Triping.dto.EmailDto;
import Triping.dto.PasswordDto;
import Triping.models.User;
import Triping.models.VerificationToken;
import Triping.repositories.UserRepository;
import Triping.repositories.VerificationTokenRepository;
import Triping.services.specifications.IAccountService;
import Triping.services.specifications.IUserService;
import Triping.tasks.OnRegistrationCompleteEvent;
import Triping.utils.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class AccountService implements IAccountService {

    @Autowired
    IUserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public User registerNewUserAccount(final AccountDto accountDto) {
        if (userService.findUserByEmail(accountDto.getEmail()) != null) {
            throw new UserAlreadyExistsException("Ya existe una cuenta registrada con " + accountDto.getEmail());
        }

        if (userService.findUserByUsername(accountDto.getUsername()) != null) {
            throw new UserAlreadyExistsException("Ya existe una cuenta registrada con nombre de usuario " + accountDto.getUsername());
        }

        //Create new user account deactivated
        final User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setEmail(accountDto.getEmail());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(accountDto.getPassword());

        user.setPassword(hashedPassword);

        return (userRepository.save(user));
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken);
    }

    @Override
    public void ResendVerificationToken(User currentUser) throws AlredyEnabledException {
        if(currentUser.isEnabled()){
            throw new AlredyEnabledException("El usuario ya se encuentra habilitado");
        }

        VerificationToken expiredToken = verificationTokenRepository.findByUser(currentUser);
        expiredToken.updateToken();

        String appUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(currentUser, appUrl, expiredToken));
    }


    @Override
    public void createVerificationToken(final User user, final String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public boolean validatePassword(User user, String password){
        String hashedPassword = user.getPassword();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
    }

    public boolean validateUsernameAndPassword(String username, String password) throws ResourceNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        String hashedPassword = user.getPassword();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
    }

    @Override
    public void changePassword(User currentUser, PasswordDto passwordDto) throws SameEntityException {
        if(!validatePassword(currentUser, passwordDto.getOldPassword())){
            throw new AccessDeniedException("Contraseña incorrecta");
        }

        if (validatePassword(currentUser, passwordDto.getNewPassword()) ){
            throw new SameEntityException("La nueva contraseña no puede coincidir con la anterior");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(passwordDto.getNewPassword());

        currentUser.setPassword(encryptedPassword);
        userRepository.save(currentUser);
    }

    @Override
    public void changeEmail(User currentUser, EmailDto emailDto) throws SameEntityException {
        if(!validatePassword(currentUser, emailDto.getPassword())){
            throw new AccessDeniedException("Contraseña incorrecta");
        }

        if(currentUser.getEmail().equals(emailDto.getEmail())){
            throw new SameEntityException("El nuevo email no puede coincidir con el anterior");
        }

        currentUser.setEmail(emailDto.getEmail());
        userRepository.save(currentUser);
    }
}
