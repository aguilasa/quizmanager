package io.github.aguilasa.quizmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserAnswerHistoryDTO extends BaseDTO {
    private UUID id;
    private UUID userId;
    private String username;
    private UUID questionId;
    private String questionText;
    private UUID answerId;
    private String answerText;
    private UUID sessionId;
    private Boolean isCorrect;
    private OffsetDateTime answerTime;
    private Integer timeTakenSeconds;
}
