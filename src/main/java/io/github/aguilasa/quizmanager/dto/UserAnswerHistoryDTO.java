package io.github.aguilasa.quizmanager.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserAnswerHistoryDTO(
    UUID id,
    UUID userId,
    String username,
    UUID questionId,
    String questionText,
    UUID answerId,
    String answerText,
    UUID sessionId,
    Boolean isCorrect,
    OffsetDateTime answerTime,
    Integer timeTakenSeconds,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {

}
