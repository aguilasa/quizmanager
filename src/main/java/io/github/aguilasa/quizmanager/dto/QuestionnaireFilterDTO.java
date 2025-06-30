package io.github.aguilasa.quizmanager.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record QuestionnaireFilterDTO(
    UUID id,
    UUID questionnaireId,
    String filterType,
    String filterValue,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
