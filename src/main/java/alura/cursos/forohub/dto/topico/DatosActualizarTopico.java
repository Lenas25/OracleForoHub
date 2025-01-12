package alura.cursos.forohub.dto.topico;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull @NotEmpty
        String titulo,
        @NotNull @NotEmpty
        String mensaje,
        Long autor,
        Long curso
) {
}
