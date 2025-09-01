package io.mremptiness.motherlodeback.app.service;

import io.mremptiness.motherlodeback.app.port.in.UserUseCase;
import io.mremptiness.motherlodeback.app.port.out.UserAccountPort;
import io.mremptiness.motherlodeback.domain.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserApplicationService implements UserUseCase {

    private final UserAccountPort accounts;
    private final PasswordEncoder encoder;

    @Override
    @Transactional(readOnly = true)
    public Profile getProfile(String email) {
        UserAccount acc = accounts.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return new Profile(acc.getEmail(), acc.getUsername(), acc.getCapitalComApiKey());
    }

    @Override
    public void changePassword(String email, String currentPassword, String newPassword) {
        UserAccount acc = accounts.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!encoder.matches(currentPassword, acc.getPasswordHash())) {
            throw new IllegalArgumentException("Contraseña actual incorrecta");
        }
        acc.setPasswordHash(encoder.encode(newPassword));
        accounts.save(acc);
    }

    @Override
    public void updateUsername(String email, String username) {
        UserAccount acc = accounts.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        acc.setUsername(username);
        accounts.save(acc);
    }

    @Override
    public void updateCapitalCom(String email, String apiKey, String passwordPlain) {
        UserAccount acc = accounts.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        acc.setCapitalComApiKey(apiKey);
        if (passwordPlain != null && !passwordPlain.isBlank()) {
            acc.setCapitalComPasswordHash(encoder.encode(passwordPlain));
        }
        accounts.save(acc);
    }
}
