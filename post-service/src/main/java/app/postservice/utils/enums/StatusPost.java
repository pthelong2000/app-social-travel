package app.postservice.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPost implements EnumBase<StatusPost>{

    PUBLISHED("00"),

    DRAFT("01"),

    DELETE("02");

    private final String value;

    public static StatusPost getEnum(String value) {
        return EnumBase.getEnum(StatusPost.class, value);
    }
}
