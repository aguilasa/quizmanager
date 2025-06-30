package io.github.aguilasa.quizmanager.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record QuestionnaireDTO(
    UUID id,
    String title,
    String description,
    UUID createdById,
    String createdByUsername,
    Integer questionCount,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
