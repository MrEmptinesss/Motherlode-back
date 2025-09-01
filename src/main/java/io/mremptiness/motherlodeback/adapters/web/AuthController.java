package io.mremptiness.motherlodeback.adapters.web;

import io.mremptiness.motherlodeback.app.port.in.AuthUseCase;
import io.mremptiness.motherlodeback.adapters.web.dto.AuthDtos.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final AuthUseCase auth;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest req) {
        String token = auth.register(req.email(), req.password());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        String token = auth.login(req.email(), req.password());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
