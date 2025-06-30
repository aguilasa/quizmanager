package io.github.aguilasa.quizmanager.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record UserQuestionnaireSessionDTO(
        UUID id,
        UUID userId,
        String username,
        UUID questionnaireId,
        String questionnaireTitle,
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        Boolean isCompleted,
        BigDecimal score,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
