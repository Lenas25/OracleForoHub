package alura.cursos.forohub.entities.topico;

import alura.cursos.forohub.dto.topico.DatosActualizarTopico;
import alura.cursos.forohub.dto.usuario.DatosActualizarUsuario;
import alura.cursos.forohub.entities.curso.Curso;
import alura.cursos.forohub.entities.perfil.Perfil;
import alura.cursos.forohub.entities.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Table(name = "topico")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    private boolean status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @PrePersist
    public void prePersist() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = new Date();
        }
    }


    public void actualizarInfo(DatosActualizarTopico datos, Curso curso, Usuario usuario) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        if (curso != null) {
            this.curso = curso;
        }
        if (usuario != null) {
            this.usuario = usuario;
        }

    }

}
