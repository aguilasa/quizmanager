package io.github.aguilasa.quizmanager.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CategoryDTO(
    UUID id,
    String name,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
