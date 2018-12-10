package org.softuni.ruk.terminal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class Terminal implements CommandLineRunner {

    @Autowired
    public Terminal() { }

    @Override
    public void run(String... args) throws Exception {
    }
}
