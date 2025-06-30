package io.github.aguilasa.quizmanager.dto;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record UserDTO(
    UUID id,
    String username,
    String email,
    String firstName,
    String lastName,
    Boolean enabled,
    Set<String> authorities,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
