package alura.cursos.forohub.entities.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findAll(Pageable paginacion);

    Optional<Usuario> findFirstByCorreoElectronico(String correoElectronico);
}
