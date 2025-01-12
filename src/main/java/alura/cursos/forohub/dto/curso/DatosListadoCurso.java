package alura.cursos.forohub.dto.curso;

import alura.cursos.forohub.entities.curso.Categoria;
import alura.cursos.forohub.entities.curso.Curso;

public record DatosListadoCurso(
        Long id,
        String nombre,
        Categoria categoria
) {
    public DatosListadoCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}
