package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityDataInitializer {

    @Bean
    public CommandLineRunner seedUsers(UserAccountRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            upsertDefaultUser(repository, passwordEncoder, "admin", "qwerty", Role.ROLE_ADMIN);
            upsertDefaultUser(repository, passwordEncoder, "librarian", "qwerty", Role.ROLE_LIBRARIAN);
            upsertDefaultUser(repository, passwordEncoder, "member", "qwerty", Role.ROLE_MEMBER);
        };
    }

    private void upsertDefaultUser(
            UserAccountRepository repository,
            PasswordEncoder passwordEncoder,
            String username,
            String rawPassword,
            Role role
    ) {
        UserAccount user = repository.findByUsername(username).orElseGet(() -> {
            UserAccount newUser = new UserAccount();
            newUser.setUsername(username);
            return newUser;
        });
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        repository.save(user);
    }
}
