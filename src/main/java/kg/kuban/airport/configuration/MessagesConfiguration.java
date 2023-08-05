package kg.kuban.airport.configuration;

import messaging.MessageHelper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

@Configuration
public class MessagesConfiguration {

//    @Bean
//    public MessageHelper messageHelper() {
//        return new MessageHelper();
//    }

//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setDefaultEncoding("ru");
//        messageSource.setUseCodeAsDefaultMessage(true);
//        messageSource.setBasename("translation/messages");
//        return messageSource;
//    }
//
//    @Bean
//    public LocaleResolver localeResolver() {
//       //CookieLocaleResolver localeResolver = new CookieLocaleResolver();
//        //localeResolver.setCookieName("lang");
//        //localeResolver.setDefaultLocale(new Locale("ru"));
//
//        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
//        localeResolver.setSupportedLocales(Arrays.asList(Locale.ENGLISH, Locale.US, new Locale("ru", "RU")));
//
//        return localeResolver;
//    }
}
