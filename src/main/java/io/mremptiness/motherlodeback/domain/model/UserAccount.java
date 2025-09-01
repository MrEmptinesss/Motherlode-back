package io.mremptiness.motherlodeback.domain.model;

import lombok.*;
import java.util.Set;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class UserAccount {
    private Long id;
    private String email;
    private String passwordHash;   // siempre hash
    @Builder.Default
    private Set<String> roles = Set.of("USER");
}
