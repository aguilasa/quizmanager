package io.github.aguilasa.quizmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDTO extends BaseDTO {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private Set<String> authorities;
}
