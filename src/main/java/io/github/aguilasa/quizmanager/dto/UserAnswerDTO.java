package io.github.aguilasa.quizmanager.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserAnswerDTO(
    UUID id,
    UUID sessionId,
    UUID questionId,
    String questionText,
    UUID answerId,
    String answerText,
    Boolean isCorrect,
    OffsetDateTime answerTime,
    Integer timeTakenSeconds,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
