package app.postservice.utils.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class TitleValidator implements ConstraintValidator<Title, String> {

    private final static Pattern pattern =
            Pattern.compile("^(?=.*[^\\s])(?=.*\\S)(?=.*[a-zA-Z0-9])[a-zA-Z0-9\\s.,!?:;'\"@#]{1,50}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        var result = true;
        if (value.length() > 50 && pattern.matcher(value).matches()) {
            result = false;
        }
        return result;
    }
}
