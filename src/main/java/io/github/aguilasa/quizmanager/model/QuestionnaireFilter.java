package io.github.aguilasa.quizmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "questionnaire_filters", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"questionnaire_id", "filter_type", "filter_value"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionnaireFilter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id", nullable = false)
    private Questionnaire questionnaire;

    @Column(name = "filter_type", nullable = false)
    private String filterType;

    @Column(name = "filter_value", nullable = false)
    private String filterValue;
}
