package io.mremptiness.motherlodeback.adapters.persistence;

import io.mremptiness.motherlodeback.app.port.out.UserAccountPort;
import io.mremptiness.motherlodeback.domain.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserAccountRepositoryAdapter implements UserAccountPort {

    private final UserAccountSpringRepository repo;

    private static UserAccount toDomain(UserAccountEntity e) {
        Set<String> roles = Arrays.stream(e.getRolesCsv().split(","))
                .filter(s -> !s.isBlank())
                .map(String::trim)
                .collect(Collectors.toSet());

        return UserAccount.builder()
                .id(e.getId())
                .email(e.getEmail())
                .passwordHash(e.getPasswordHash())
                .roles(roles)
                .build();
    }

    private static UserAccountEntity toEntity(UserAccount d) {
        String csv = String.join(",", d.getRoles());
        return UserAccountEntity.builder()
                .id(d.getId())
                .email(d.getEmail())
                .passwordHash(d.getPasswordHash())
                .rolesCsv(csv.isBlank() ? "USER" : csv)
                .build();
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return repo.findByEmail(email).map(UserAccountRepositoryAdapter::toDomain);
    }

    @Override
    public UserAccount save(UserAccount user) {
        return toDomain(repo.save(toEntity(user)));
    }
}
