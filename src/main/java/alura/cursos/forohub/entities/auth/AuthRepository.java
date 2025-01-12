package alura.cursos.forohub.entities.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthRepository extends JpaRepository<UsuarioAuth, Long> {

    UserDetails findByCorreoElectronico(String correoElectronico);
}
