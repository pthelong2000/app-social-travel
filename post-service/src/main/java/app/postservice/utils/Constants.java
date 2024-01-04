package app.postservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Constants {


    public static final String BAD_REQUEST ="message.error.response.400";

    public static final String UNAUTHORIZED = "message.error.response.401";

    public static final String FORBIDDEN = "message.error.response.403";

    public static final String NOT_FOUND = "message.error.response.404";


}
