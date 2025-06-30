package io.github.aguilasa.quizmanager.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AuthorityDTO(
    UUID id,
    UUID userId,
    String authority,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
