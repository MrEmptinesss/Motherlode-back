package io.mremptiness.motherlodeback.domain.model;

import lombok.*;
import java.util.Set;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class UserAccount {
    private Long id;
    private String email;
    private String passwordHash;
    @Builder.Default
    private Set<String> roles = Set.of("USER");
    private String username;
    private String capitalComApiKey;
    private String capitalComPasswordHash;
}
