package com.example.fileservice.utils.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ImageSizeValidator implements ConstraintValidator<ImageSize, MultipartFile> {

    private static final long MAX_FILE_SIZE = 52428800;

    @Override
    public boolean isValid(MultipartFile imageFile, ConstraintValidatorContext context) {
          if (Objects.isNull(imageFile)) {
                return false;
          }

        long fileSize = imageFile.getSize();
        if (fileSize > MAX_FILE_SIZE) {
            return false;
        }
        return true;
    }


}
