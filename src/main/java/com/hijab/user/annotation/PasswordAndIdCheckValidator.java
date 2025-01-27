package com.hijab.user.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.server.ServerErrorException;

import java.lang.reflect.Field;

public class PasswordAndIdCheckValidator implements
    ConstraintValidator<PasswordAndIdCheck, Object> {

    private String message;
    private String userEmail;
    private String password;

    @Override
    public void initialize(PasswordAndIdCheck constraintAnnotation) {
        message = constraintAnnotation.message();
        userEmail = constraintAnnotation.userEmail();
        password = constraintAnnotation.password();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        int invalidCount = 0;
        String email = getFieldValue(value, userEmail);
        String pwd = getFieldValue(value, password);

        if (isContainId(email, pwd)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(email)
                .addConstraintViolation();
            invalidCount += 1;
        }
        return invalidCount == 0;
    }

    private boolean isContainId(String email, String password) {
        String[] array = email.split("@");
        return password.contains(array[0]);
    }

    private String getFieldValue(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        Field dateField;
        try {
            dateField = clazz.getDeclaredField(fieldName);
            dateField.setAccessible(true);
            Object target = dateField.get(object);
            if (!(target instanceof String)) {
                throw new ClassCastException("casting exception");
            }
            return (String) target;
        } catch (NoSuchFieldException e) {
            throw new ServerErrorException("NoSuchFieldException", e);
//            log.error("NoSuchFieldException", e);
        } catch (IllegalAccessException e) {
            throw new ServerErrorException("IllegalAccessException", e);
//            log.error("IllegalAccessException", e);
        }
    }
}
