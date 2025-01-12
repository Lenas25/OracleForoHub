package alura.cursos.forohub.dto.usuario;

import alura.cursos.forohub.dto.perfil.DatosPerfil;
import alura.cursos.forohub.entities.usuario.Usuario;

public record DatosListadoUsuario(
        Long id,
        String nombre,
        String correo_electronico,
        String contrasena,
        DatosPerfil perfil
) {
    public DatosListadoUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getCorreoElectronico(), usuario.getContrasena(), new DatosPerfil(usuario.getPerfil().getNombre()));
    }
}
