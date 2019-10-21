package com.initializer.Employeedetails.MessageUtil;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageUtil implements MessageSourceAware {

    private MessageSource messageSource;

    public String getMessage(String tag) {
        return messageSource.getMessage(tag,null, Locale.US);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
