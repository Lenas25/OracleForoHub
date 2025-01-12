package alura.cursos.forohub.dto.security;

import jakarta.validation.constraints.Null;

public record Response(
        String message,
        @Null
        Object data
) {
}
