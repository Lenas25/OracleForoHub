package alura.cursos.forohub.controller;

import alura.cursos.forohub.dto.curso.DatosRegistroCurso;
import alura.cursos.forohub.dto.security.Response;
import alura.cursos.forohub.entities.curso.Categoria;
import alura.cursos.forohub.entities.curso.Curso;
import alura.cursos.forohub.entities.curso.CursoRepository;
import alura.cursos.forohub.entities.topico.Topico;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("curso")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;


    @GetMapping()
    public List<Curso> Listar() {
        return cursoRepository.findAll();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        if (curso.isPresent()) {
            cursoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Curso eliminado", null));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity agregar(@RequestBody @Valid DatosRegistroCurso datos) {
        try {
            Categoria categoria = Categoria.valueOf(String.valueOf(datos.categoria()));

            Curso newCurso = new Curso(null, datos.nombre(), categoria);
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Curso agregado", cursoRepository.save(newCurso)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("Error al registrar el curso", e));
        }
    }

}
