package alura.cursos.forohub.entities.topico;

import alura.cursos.forohub.entities.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicoRepository  extends JpaRepository<Topico, Long> {
    Page<Topico> findAll(Pageable paginacion);

    Optional<Topico> findTopicoByTituloOrMensaje(String titulo, String mensaje);

    Optional<Topico> findByTituloOrMensaje(String titulo, String mensaje);
}
