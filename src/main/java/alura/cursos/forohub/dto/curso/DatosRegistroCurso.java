package alura.cursos.forohub.dto.curso;

import alura.cursos.forohub.entities.curso.Categoria;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public record DatosRegistroCurso(
        @NotNull
        String nombre,
        @NotNull
        Categoria categoria
) {

}
