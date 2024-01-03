package app.postservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Contants {

    private static MessageSource messageSource;

    public static String BAD_ = getMessage("error.requiredField");
    public static String ERROR_INVALID_EMAIL = getMessage("error.invalidEmail");


    private static String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }
}
