package io.mremptiness.motherlodeback.app.port.in;

public interface AuthUseCase {
    String register(String email, String rawPassword);
    String login(String email, String rawPassword);
}
