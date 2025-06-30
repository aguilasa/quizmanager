package io.github.aguilasa.quizmanager.dto;

import io.github.aguilasa.quizmanager.model.DifficultyLevel;
import java.time.OffsetDateTime;
import java.util.UUID;

public record QuestionDTO(
    UUID id,
    String questionText,
    String explanation,
    DifficultyLevel difficulty,
    String topic,
    CategoryDTO category,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
