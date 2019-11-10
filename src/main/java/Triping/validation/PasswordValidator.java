package Triping.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
      private Pattern pattern;
      private Matcher matcher;
      private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%?\\^&\\*]).{8,})";

      // This method is guaranteed to be called before any use of this instance for validation
      @Override
      public void initialize(ValidPassword constraint) {
      }

      @Override
      public boolean isValid(String password, ConstraintValidatorContext context) {
         return (validatePassword(password));
      }

      private boolean validatePassword (String password){
         pattern = Pattern.compile(PASSWORD_PATTERN);
         matcher = pattern.matcher(password);
         return matcher.matches();
      }
}
