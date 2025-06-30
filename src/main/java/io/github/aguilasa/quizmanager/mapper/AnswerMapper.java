package io.github.aguilasa.quizmanager.mapper;

import io.github.aguilasa.quizmanager.dto.AnswerDTO;
import io.github.aguilasa.quizmanager.model.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * Mapper para conversão entre a entidade Answer e o DTO AnswerDTO.
 * Utiliza MapStruct Spring Extensions para integração com Spring.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnswerMapper extends Converter<Answer, AnswerDTO> {

    /**
     * Método de conversão exigido pela interface Converter do Spring.
     * 
     * @param answer a entidade a ser convertida
     * @return o DTO resultante
     */
    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "answerText", source = "answerText")
    @Mapping(target = "isCorrect", source = "correct")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    AnswerDTO convert(Answer answer);

    /**
     * Converte um DTO AnswerDTO para uma entidade Answer.
     * Ignora os relacionamentos com Question, UserAnswer e UserAnswerHistory,
     * que devem ser tratados separadamente.
     * 
     * @param answerDTO o DTO a ser convertido
     * @return a entidade resultante
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "answerText", source = "answerText")
    @Mapping(target = "isCorrect", source = "isCorrect")
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "userAnswers", ignore = true)
    @Mapping(target = "answerHistory", ignore = true)
    Answer toEntity(AnswerDTO answerDTO);

    /**
     * Converte uma lista de entidades Answer para uma lista de DTOs AnswerDTO.
     * 
     * @param answers a lista de entidades a ser convertida
     * @return a lista de DTOs resultante
     */
    List<AnswerDTO> toDtoList(List<Answer> answers);

    /**
     * Converte uma lista de DTOs AnswerDTO para uma lista de entidades Answer.
     * 
     * @param answerDTOs a lista de DTOs a ser convertida
     * @return a lista de entidades resultante
     */
    List<Answer> toEntityList(List<AnswerDTO> answerDTOs);
}
