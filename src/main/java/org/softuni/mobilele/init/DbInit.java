package org.softuni.mobilele.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {

    private final String defaultAdminPass;

    public DbInit(@Value("${mobilele.default.admin.pass}") String defaultAdminPass){

        this.defaultAdminPass = defaultAdminPass;
    }
    @Override
    public void run(String... args) throws Exception {
        //TODO:

    }
}
