package Triping.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import Triping.dto.AccountDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final AccountDto user = (AccountDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}