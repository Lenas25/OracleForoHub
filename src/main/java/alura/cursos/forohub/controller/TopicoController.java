package alura.cursos.forohub.controller;

import alura.cursos.forohub.dto.security.Response;
import alura.cursos.forohub.dto.topico.DatosActualizarTopico;
import alura.cursos.forohub.dto.topico.DatosListadoTopico;
import alura.cursos.forohub.dto.topico.DatosRegistroTopico;
import alura.cursos.forohub.dto.usuario.DatosActualizarUsuario;
import alura.cursos.forohub.dto.usuario.DatosListadoUsuario;
import alura.cursos.forohub.dto.usuario.DatosRegistroUsuario;
import alura.cursos.forohub.entities.curso.Curso;
import alura.cursos.forohub.entities.curso.CursoRepository;
import alura.cursos.forohub.entities.perfil.Perfil;
import alura.cursos.forohub.entities.topico.Topico;
import alura.cursos.forohub.entities.topico.TopicoRepository;
import alura.cursos.forohub.entities.usuario.Usuario;
import alura.cursos.forohub.entities.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("topico")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public Page<DatosListadoTopico> listar(@PageableDefault(size = 10) Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(DatosListadoTopico::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity obtener(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Topico obtenido correctamente", topico.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("No existe el topico",null));
    }

    @PostMapping
    public ResponseEntity crearTopico(@RequestBody @Valid DatosRegistroTopico datos) {
        try {
            var topicoExisting = topicoRepository.findTopicoByTituloOrMensaje(datos.titulo(), datos.mensaje());
            if (topicoExisting.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Ya existe el topico", topicoExisting));
            }
            var user = usuarioRepository.findById(datos.autor());
            var curso = cursoRepository.findById(datos.curso());
            if (!user.isPresent() || !curso.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Usuario o curso no encontrado", user));
            }

            var newTopico = new Topico(null, datos.titulo(), datos.mensaje(), new Date(), true, curso.get(), user.get());
            topicoRepository.save(newTopico);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Topico creado correctamente", newTopico));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("Error al actualizar el usuario", e));
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Topico eliminado", null));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/topicos/{id}")
    @Transactional
    public ResponseEntity<Response> actualizarTopico(@PathVariable Long id, @RequestBody DatosActualizarTopico datos) {
        try {
            Optional<Topico> optionalTopico = topicoRepository.findById(id);
            if (optionalTopico.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response("El tópico con no fue encontrado", null));
            }

            Topico topico = optionalTopico.get();

            Optional<Topico> topicoDuplicado = topicoRepository
                    .findByTituloOrMensaje(datos.titulo(), datos.mensaje());
            if (topicoDuplicado.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new Response("Ya existe un tópico con el mismo título y mensaje.", null));
            }

            if (datos.autor() != null) {
                Optional<Usuario> usuario = usuarioRepository.findById(datos.autor());
                if (usuario.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new Response("El usuario no existe.", null));
                }
                topico.setUsuario(usuario.get());
            }

            if (datos.curso() != null) {
                Optional<Curso> curso = cursoRepository.findById(datos.curso());
                if (curso.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new Response("El curso no existe.", null));
                }
                topico.setCurso(curso.get());
            }

            topico.setTitulo(datos.titulo());
            topico.setMensaje(datos.mensaje());
            topicoRepository.save(topico);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Response("Tópico actualizado correctamente.", topico));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("Error al actualizar el tópico.", e.getMessage()));
        }
    }


}
