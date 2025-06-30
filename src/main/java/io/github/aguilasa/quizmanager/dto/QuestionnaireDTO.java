package io.github.aguilasa.quizmanager.dto;

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
public class QuestionnaireDTO extends BaseDTO {
    private UUID id;
    private String title;
    private String description;
    private UUID createdById;
    private String createdByUsername;
    private Integer questionCount;
}
