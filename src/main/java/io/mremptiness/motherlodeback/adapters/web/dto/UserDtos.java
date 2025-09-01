package io.mremptiness.motherlodeback.adapters.web.dto;

public final class UserDtos {

    public record UserProfileResponse(
            String email,
            String username,
            String capitalComApiKey
    ) {}

    public record ChangePasswordRequest(String currentPassword, String newPassword) {}

    public record UpdateUsernameRequest(String username) {}

    public record UpdateCapitalComRequest(String apiKey, String password) {}
}
