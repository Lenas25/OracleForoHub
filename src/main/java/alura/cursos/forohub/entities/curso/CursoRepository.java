package alura.cursos.forohub.entities.curso;

import alura.cursos.forohub.entities.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
