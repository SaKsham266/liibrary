package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Configuration
public class SecurityDataInitializer {

    @Bean
    public CommandLineRunner seedUsers(
            UserAccountRepository repository,
            PasswordEncoder passwordEncoder,
            @Value("${library.security.default-password}") String defaultPassword
    ) {
        return args -> {
            if (!StringUtils.hasText(defaultPassword)) {
                throw new IllegalStateException("DEFAULT_USER_PASSWORD must be set before seeding users.");
            }
            upsertDefaultUser(repository, passwordEncoder, "admin", defaultPassword, Role.ROLE_ADMIN);
            upsertDefaultUser(repository, passwordEncoder, "librarian", defaultPassword, Role.ROLE_LIBRARIAN);
            upsertDefaultUser(repository, passwordEncoder, "member", defaultPassword, Role.ROLE_MEMBER);
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
