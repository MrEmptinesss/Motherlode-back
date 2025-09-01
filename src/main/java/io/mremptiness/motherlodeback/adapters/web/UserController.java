package io.mremptiness.motherlodeback.adapters.web;

import io.mremptiness.motherlodeback.app.port.in.UserUseCase;
import io.mremptiness.motherlodeback.adapters.web.dto.UserDtos.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User")
@Slf4j
public class UserController {

    private final UserUseCase users;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> me(Principal principal) {
        String who = pr(principal);
        log.info("[UserController] GET /user/me principal={}", who);
        try {
            var p = users.getProfile(who);
            log.info("[UserController] OK /user/me email={} username={}", p.email(), p.username());
            return ResponseEntity.ok(new UserProfileResponse(
                    p.email(), p.username(), p.capitalComApiKey()
            ));
        } catch (Exception ex) {
            log.error("[UserController] ERROR /user/me principal={} -> {}", who, ex.toString());
            throw ex;
        }
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(Principal principal,
                                               @Valid @RequestBody ChangePasswordRequest req) {
        String who = pr(principal);
        log.info("[UserController] PUT /user/password principal={} (body masked)", who);
        try {
            users.changePassword(who, req.currentPassword(), req.newPassword());
            log.info("[UserController] OK /user/password principal={}", who);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("[UserController] ERROR /user/password principal={} -> {}", who, ex.toString());
            throw ex;
        }
    }

    @PutMapping("/username")
    public ResponseEntity<Void> updateUsername(Principal principal,
                                               @Valid @RequestBody UpdateUsernameRequest req) {
        String who = pr(principal);
        log.info("[UserController] PUT /user/username principal={} username={}", who, safe(req.username()));
        try {
            users.updateUsername(who, req.username());
            log.info("[UserController] OK /user/username principal={}", who);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("[UserController] ERROR /user/username principal={} -> {}", who, ex.toString());
            throw ex;
        }
    }

    @PutMapping("/capitalcom")
    public ResponseEntity<Void> updateCapitalCom(Principal principal,
                                                 @Valid @RequestBody UpdateCapitalComRequest req) {
        String who = pr(principal);
        log.info("[UserController] PUT /user/capitalcom principal={} apiKey={} (password masked)",
                who, safe(req.apiKey()));
        try {
            users.updateCapitalCom(who, req.apiKey(), req.password());
            log.info("[UserController] OK /user/capitalcom principal={}", who);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("[UserController] ERROR /user/capitalcom principal={} -> {}", who, ex.toString());
            throw ex;
        }
    }

    // --- helpers de log ---
    private static String pr(Principal p) { return p != null ? p.getName() : "null"; }
    private static String safe(String v) {
        if (v == null) return "null";
        var t = v.trim();
        if (t.isEmpty()) return "\"\"";
        // muestra algo pero sin exponer todo
        return t.length() <= 4 ? "***" : (t.substring(0, 2) + "***" + t.substring(t.length() - 2));
    }
}
