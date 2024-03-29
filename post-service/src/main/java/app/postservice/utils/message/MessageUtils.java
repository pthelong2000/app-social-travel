package app.postservice.utils.message;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("messageUtils")
public class MessageUtils {

    private static MessageSource messageSource;

    @Autowired
    public MessageUtils(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    public static String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }
}
