package app.postservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Constants {

    private static MessageSource messageSource;

    public static final String BAD_REQUEST = getMessage("message.error.response.400");

    public static final String UNAUTHORIZED = getMessage("message.error.response.401");

    public static final String FORBIDDEN = getMessage("message.error.response.403");

    public static final String NOT_FOUND = getMessage("message.error.response.404");

    private static String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }
}
