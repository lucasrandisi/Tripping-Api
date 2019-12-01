package Triping.services.specifications;

import Triping.dto.AccountDto;
import Triping.dto.EmailDto;
import Triping.dto.PasswordDto;
import Triping.models.User;
import Triping.models.VerificationToken;
import Triping.utils.exceptions.AlredyEnabledException;
import Triping.utils.exceptions.ResourceNotFoundException;
import Triping.utils.exceptions.SameEntityException;
import Triping.utils.exceptions.UserAlreadyExistsException;

public interface IAccountService {
    User registerNewUserAccount(AccountDto accountDto) throws UserAlreadyExistsException, ResourceNotFoundException;

    boolean validatePassword(User user, String password);

    VerificationToken getVerificationToken(String token);

    void createVerificationToken(User user, String token);

    void changePassword(User currentUser, PasswordDto passwordDto) throws SameEntityException;

    void ResendVerificationToken(User currentUser) throws AlredyEnabledException;

    void changeEmail(User currentUser, EmailDto emailDto) throws SameEntityException;

    boolean validateUsernameAndPassword(String username, String password) throws ResourceNotFoundException;

    String currentAuthenticatedUser();
}
