package io.mremptiness.motherlodeback.app.service;

import io.mremptiness.motherlodeback.app.port.in.AuthUseCase;
import io.mremptiness.motherlodeback.app.port.out.UserAccountPort;
import io.mremptiness.motherlodeback.domain.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserAccountPort userPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public String register(String email, String rawPassword) {
        userPort.findByEmail(email).ifPresent(u -> { throw new IllegalArgumentException("Email ya registrado"); });
        UserAccount toSave = UserAccount.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .build();
        UserAccount saved = userPort.save(toSave);
        return jwtService.issueToken(saved.getEmail(), saved.getId(), saved.getRoles());
    }

    @Override
    public String login(String email, String rawPassword) {
        UserAccount user = userPort.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
        return jwtService.issueToken(user.getEmail(), user.getId(), user.getRoles());
    }
}
