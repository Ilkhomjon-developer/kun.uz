package dasturlash.uz.service;

import dasturlash.uz.enums.AppLanguageEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ResourceBundleService {

    private final ResourceBundleMessageSource messageSource;

    public String getMessage(String code, AppLanguageEnum lang){
        return messageSource.getMessage(code, null, Locale.of(lang.name()));
    }
}
