package org.softuni.mobilele.testutils;

import org.softuni.mobilele.model.entity.UserRole;
import org.softuni.mobilele.model.enums.UserRoleEnum;
import org.softuni.mobilele.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBInit implements CommandLineRunner {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public void run(String... args) throws Exception {
    if (userRoleRepository.count() == 0) {
        userRoleRepository.saveAll(List.of(
                new UserRole().setRole(UserRoleEnum.USER),
                new UserRole().setRole(UserRoleEnum.ADMIN)
        ));
    }
    }
}
