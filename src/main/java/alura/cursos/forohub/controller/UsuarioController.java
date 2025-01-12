package alura.cursos.forohub.controller;

import alura.cursos.forohub.dto.security.Response;
import alura.cursos.forohub.dto.usuario.DatosActualizarUsuario;
import alura.cursos.forohub.dto.usuario.DatosListadoUsuario;
import alura.cursos.forohub.entities.perfil.Perfil;
import alura.cursos.forohub.entities.perfil.PerfilRepository;
import alura.cursos.forohub.entities.usuario.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public Page<DatosListadoUsuario> listar(@PageableDefault(size = 10) Pageable paginacion) {
        return usuarioRepository.findAll(paginacion).map(DatosListadoUsuario::new);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Usuario eliminado", null));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizar(@PathVariable Long id, @RequestBody DatosActualizarUsuario datos) {
        try {
            var usuario = usuarioRepository.getReferenceById(id);

            Perfil perfil = usuario.getPerfil();
            if (datos.perfil() != null) {
                Optional<Perfil> perfilExisting = perfilRepository.findFirstByNombre(datos.perfil().nombre());
                if (perfilExisting.isPresent()) {
                    perfil = perfilExisting.get();
                } else {
                    perfil = perfilRepository.save(new Perfil(null, datos.perfil().nombre()));
                }
            }

            usuario.actualizarInfo(datos, passwordEncoder, perfil);
            DatosListadoUsuario datosListado = new DatosListadoUsuario(usuario);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Response("Usuario actualizado correctamente", datosListado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("Error al actualizar el usuario", e));
        }
    }


}
