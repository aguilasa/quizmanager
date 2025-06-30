package io.github.aguilasa.quizmanager.mapper;

import io.github.aguilasa.quizmanager.dto.AnswerDTO;
import io.github.aguilasa.quizmanager.model.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;

/**
 * Mapper para conversão de AnswerDTO para Answer.
 * Complementa o AnswerMapper para permitir conversão bidirecional.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnswerDTOMapper extends Converter<AnswerDTO, Answer> {

    /**
     * Método de conversão exigido pela interface Converter do Spring.
     * Converte um DTO AnswerDTO para uma entidade Answer.
     * 
     * @param answerDTO o DTO a ser convertido
     * @return a entidade resultante
     */
    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "answerText", source = "answerText")
    @Mapping(target = "isCorrect", source = "isCorrect")
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "userAnswers", ignore = true)
    @Mapping(target = "answerHistory", ignore = true)
    Answer convert(AnswerDTO answerDTO);
}
