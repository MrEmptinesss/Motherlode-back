package io.mremptiness.motherlodeback.adapters.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_account", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_email", columnNames = "email")
})
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class UserAccountEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)  private String email;
    @Column(nullable = false, name = "password_hash") private String passwordHash;

    // roles serializados como CSV (ya los mapeas en el adapter)
    @Column(name = "roles_csv", nullable = false) private String rolesCsv;

    // NUEVAS COLUMNAS (alineadas con tu dominio)
    @Column private String username;

    @Column(name = "capitalcom_api_key")          private String capitalComApiKey;
    @Column(name = "capitalcom_password_hash")    private String capitalComPasswordHash;
}
