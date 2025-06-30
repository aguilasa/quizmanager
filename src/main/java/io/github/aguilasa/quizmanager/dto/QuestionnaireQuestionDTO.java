package io.github.aguilasa.quizmanager.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record QuestionnaireQuestionDTO(
    UUID id,
    UUID questionnaireId,
    UUID questionId,
    Integer questionOrder,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
