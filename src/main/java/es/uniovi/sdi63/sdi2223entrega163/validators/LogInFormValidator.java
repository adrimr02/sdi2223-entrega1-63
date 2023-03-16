package es.uniovi.sdi63.sdi2223entrega163.validators;

import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LogInFormValidator implements Validator {

    @Autowired
    private UsersService usersService;
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Error.empty");

        if (usersService.getUserByEmail(user.getEmail()) == null) {
            errors.rejectValue("dni", "Error.login.email.nonexistentUser");}
        if (!usersService.authenticate(user.getEmail(), user.getPassword())) {
            errors.rejectValue("password", "Error.login.invalidPassword");
        }
    }

}
