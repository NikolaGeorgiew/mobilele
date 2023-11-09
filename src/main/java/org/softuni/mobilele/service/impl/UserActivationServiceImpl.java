package org.softuni.mobilele.service.impl;

import org.softuni.mobilele.model.events.UserRegisteredEvent;
import org.softuni.mobilele.service.EmailService;
import org.softuni.mobilele.service.UserActivationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserActivationServiceImpl implements UserActivationService{
    private final EmailService emailService;
    public UserActivationServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener(UserRegisteredEvent.class)
    @Override
    public void userRegistered(UserRegisteredEvent event) {
        //TODO: Add activation links
       emailService.sendRegistrationEmail(event.getUserEmail(), event.getUsernames());
    }

    @Override
    public void cleanUpObsoleteActivationLinks() {
        //TODO: Implement
        System.out.println("NOT YET");
    }

}
