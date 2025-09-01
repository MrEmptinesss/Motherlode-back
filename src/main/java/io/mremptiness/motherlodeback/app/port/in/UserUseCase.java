package io.mremptiness.motherlodeback.app.port.in;

public interface UserUseCase {

    // Lo que se devuelve al front
    record Profile(String email, String username, String capitalComApiKey) {}

    Profile getProfile(String email);

    void changePassword(String email, String currentPassword, String newPassword);

    void updateUsername(String email, String username);

    void updateCapitalCom(String email, String apiKey, String passwordPlain);
}
