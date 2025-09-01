package io.mremptiness.motherlodeback.app.port.out;

import io.mremptiness.motherlodeback.domain.model.UserAccount;
import java.util.Optional;

public interface UserAccountPort {
    Optional<UserAccount> findByEmail(String email);
    UserAccount save(UserAccount user);
}
