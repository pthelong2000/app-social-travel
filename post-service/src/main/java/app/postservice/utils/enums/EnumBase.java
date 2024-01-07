package app.postservice.utils.enums;

import java.util.HashMap;
import java.util.Map;

public interface  EnumBase<E extends Enum<E>> {


    static <E extends EnumBase<?>> E getEnum(Class<E> enumClass, String value) {
        if (value == null) {
            return null;
        }
        Map<String, E> enumMap = createEnumMap(enumClass);
        return enumMap.get(value.toLowerCase());
    }


    static <E extends EnumBase<?>> boolean hasValue(Class<E> enumClass, String value) {
        if (value == null) {
            return false;
        }
        Map<String, E> enumMap = createEnumMap(enumClass);
        return enumMap.containsKey(value.toLowerCase());
    }


    static <E extends EnumBase<?>> Map<String,E> createEnumMap(Class<E> enumClass) {
        Map<String, E> enumMap = new HashMap<>();
        for (E enumValue : enumClass.getEnumConstants()) {
            enumMap.put(enumValue.toString().toLowerCase(), enumValue);
        }
        return enumMap;
    }

}
