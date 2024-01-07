package app.postservice.utils.validation;



import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ContentValidator implements ConstraintValidator<Content, String> {

    private final static Pattern pattern =
            Pattern.compile("^(?=.*[^\\s])(?=.*\\S)(?=.*[a-zA-Z0-9])[a-zA-Z0-9\\s.,!?:;'\"@#]{1,280}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        var result = true;
        if (StringUtils.hasLength(value)) {
            result = pattern.matcher(value).matches();
        }
        return result;
    }
}
