package vn.softdream.autotest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import vn.softdream.autotest.constants.BaseConstants;

/**
 * Supports internationalization (i18n) and integration with validation <p>
 * Useful when using Hibernate Validator with annotations like @NotNull, @Size,...
 * @author thoaidc
 */
@Configuration
public class MessageSourceConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MessageSourceConfiguration.class);

    @Bean
    public MessageSource messageSource() {
        log.debug("Configured MessageSource for translate message I18n");
        // Provides a mechanism to load notifications from .properties files to support i18n
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // Set the location of the message files
        // Spring will look for files by name messages_{locale}.properties
        messageSource.setBasenames(BaseConstants.MESSAGE_SOURCE_BASENAME);
        messageSource.setDefaultEncoding(BaseConstants.MESSAGE_SOURCE_ENCODING);
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        log.debug("Configured MessageSource for hibernate validation");
        // Connect the validation to MessageSource to get error messages from message bundles
        // When a validation error occurs, Spring looks for the error message from the .properties files provided
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
