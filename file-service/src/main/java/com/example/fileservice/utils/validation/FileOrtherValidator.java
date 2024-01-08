package com.example.fileservice.utils.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FileOrtherValidator implements ConstraintValidator<FileOrther, MultipartFile> {

    private static final long MAX_FILE_SIZE = 30 * 1024 * 1024;

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (Objects.isNull(file)) {
            return false;
        }

        long fileSize = file.getSize();
        if (fileSize > MAX_FILE_SIZE) {
            return false;
        }
        return true;
    }
}
