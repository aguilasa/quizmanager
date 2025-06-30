package io.github.aguilasa.quizmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserQuestionnaireSessionDTO extends BaseDTO {
    private UUID id;
    private UUID userId;
    private String username;
    private UUID questionnaireId;
    private String questionnaireTitle;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private boolean isCompleted;
    private BigDecimal score;
}
