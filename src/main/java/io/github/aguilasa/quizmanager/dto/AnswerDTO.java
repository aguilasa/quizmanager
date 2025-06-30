package io.github.aguilasa.quizmanager.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AnswerDTO(
    UUID id,
    String answerText,
    Boolean isCorrect,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
