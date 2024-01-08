package com.example.fileservice.utils.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;
import java.util.Objects;

public class VideoSizeValidator implements ConstraintValidator<ImageSize, File> {

    private static final long MAX_FILE_SIZE = 536870912;

    @Override
    public boolean isValid(File imageFile, ConstraintValidatorContext context) {
        if (Objects.isNull(imageFile)) {
            return false;
        }

        long fileSize = imageFile.length();
        if (fileSize > MAX_FILE_SIZE) {
            return false;
        }
        return true;
    }


}