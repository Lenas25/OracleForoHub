package alura.cursos.forohub.dto.perfil;

import jakarta.validation.constraints.NotBlank;

public record DatosPerfil(
        @NotBlank
        String nombre
) {
}
