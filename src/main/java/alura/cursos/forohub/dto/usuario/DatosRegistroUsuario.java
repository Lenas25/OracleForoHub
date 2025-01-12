package alura.cursos.forohub.dto.usuario;

import alura.cursos.forohub.dto.perfil.DatosPerfil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroUsuario(

        @NotBlank
        String nombre,
        @Email
        String correoElectronico,
        @NotBlank
        String contrasena,
        @NotNull @Valid DatosPerfil perfil
) {
}
