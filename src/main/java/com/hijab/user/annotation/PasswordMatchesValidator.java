package com.hijab.user.annotation;

import com.hijab.user.dto.SignUpRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        SignUpRequest user = (SignUpRequest) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
