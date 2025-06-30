package io.github.aguilasa.quizmanager.config;

import org.mapstruct.extensions.spring.SpringMapperConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração para habilitar o MapStruct Spring Extensions.
 * Esta classe ativa as extensões do MapStruct para Spring que permitem
 * injeção de dependências e outros recursos Spring nos mappers.
 */
@Configuration
@SpringMapperConfig
public class MapstructConfig {
    // A anotação @SpringMapperConfig é suficiente para configurar as extensões
}
