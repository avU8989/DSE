package swa.meet.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class ValidatorUUID implements ConstraintValidator<ValidUUID, String> {
    @Override
    public void initialize(ValidUUID constraintAnnotation) {
    }

    @Override
    public boolean isValid(String uuid, ConstraintValidatorContext context) {
        if (uuid == null || uuid.isEmpty()) {
            return true; // consider null and empty as valid. Use @NotNull and @NotEmpty for that validation
        }
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
