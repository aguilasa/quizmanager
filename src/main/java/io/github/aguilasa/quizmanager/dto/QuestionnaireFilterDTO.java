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
public class QuestionnaireFilterDTO extends BaseDTO {
    private UUID id;
    private UUID questionnaireId;
    private String filterType;
    private String filterValue;
}
