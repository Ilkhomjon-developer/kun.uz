package dasturlash.uz.service;

import dasturlash.uz.enums.AppLanguageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ResourceBundleService {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    public String getMessage(String code, AppLanguageEnum lang){
        return messageSource.getMessage(code, null, Locale.of(lang.name()));
    }
}
