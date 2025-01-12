package alura.cursos.forohub.dto.usuario;

import alura.cursos.forohub.dto.perfil.DatosPerfil;

public record DatosActualizarUsuario(
        String nombre,
        String correoElectronico,
        String contrasena,
        DatosPerfil perfil
) {
}
