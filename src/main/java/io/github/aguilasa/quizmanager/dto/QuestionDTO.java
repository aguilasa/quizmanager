package io.github.aguilasa.quizmanager.dto;

import io.github.aguilasa.quizmanager.model.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class QuestionDTO extends BaseDTO {
    private UUID id;
    private String questionText;
    private String explanation;
    private DifficultyLevel difficulty;
    private String topic;
    private CategoryDTO category;
}
