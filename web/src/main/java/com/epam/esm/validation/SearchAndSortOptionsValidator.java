package com.epam.esm.validation;

import com.epam.esm.model.SearchAndSortGiftCertificateOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
public class SearchAndSortOptionsValidator implements Validator {
    private static final String NAME_VALIDATION_PATTERN = ".{1,50}";
    private static final String DESCRIPTION_VALIDATION_PATTERN = ".{1,500}";
    private static final String SORT_VALIDATION_PATTERN = "^.{1,30}[,](ASC|DESC)";
    private static final String VALIDATE_NAME_MESSAGE = "validate.name";
    private static final String VALIDATE_TAG_NAME_MESSAGE = "validate.tagName";
    private static final String VALIDATE_DESCRIPTION_MESSAGE = "validate.description";
    private static final String VALIDATE_SORT_MESSAGE = "validate.sort";
    private final MessageSource messageSource;

    @Autowired
    public SearchAndSortOptionsValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SearchAndSortGiftCertificateOptions.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Locale locale = Locale.getDefault();
        SearchAndSortGiftCertificateOptions options = (SearchAndSortGiftCertificateOptions) target;
        if (options.getTag() != null && !options.getTag().matches(NAME_VALIDATION_PATTERN)) {
            errors.reject("400", messageSource.getMessage(VALIDATE_TAG_NAME_MESSAGE, null, locale));
        }
        if (options.getName() != null && !options.getName().matches(NAME_VALIDATION_PATTERN)) {
            errors.reject("400", messageSource.getMessage(VALIDATE_NAME_MESSAGE, null, locale));
        }
        if (options.getDescription() != null && !options.getDescription().matches(DESCRIPTION_VALIDATION_PATTERN)) {
            errors.reject("400", messageSource.getMessage(VALIDATE_DESCRIPTION_MESSAGE, null, locale));
        }
        if (options.getSort() != null && !options.getSort().matches(SORT_VALIDATION_PATTERN)) {
            errors.reject("400", messageSource.getMessage(VALIDATE_SORT_MESSAGE, null, locale));
        }
    }
}
