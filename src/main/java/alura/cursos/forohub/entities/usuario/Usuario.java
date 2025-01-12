package alura.cursos.forohub.entities.usuario;

import alura.cursos.forohub.dto.usuario.DatosActualizarUsuario;
import alura.cursos.forohub.entities.perfil.Perfil;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Table(name = "usuario")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    private String contrasena;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    public void actualizarInfo(DatosActualizarUsuario datos, PasswordEncoder passwordEncoder, Perfil perfil) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.correoElectronico() != null) {
            this.correoElectronico = datos.correoElectronico();
        }
        if (datos.contrasena() != null) {
            this.contrasena = passwordEncoder.encode(datos.contrasena());
        }
        if (perfil != null) {
            this.perfil = perfil;
        }
    }



}
