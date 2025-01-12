package alura.cursos.forohub.dto.topico;

import alura.cursos.forohub.dto.curso.DatosListadoCurso;
import alura.cursos.forohub.dto.usuario.DatosListadoUsuario;
import alura.cursos.forohub.entities.topico.Topico;

import java.util.Date;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        Date fechaCreacion,
        boolean status,
        DatosListadoUsuario usuario,
        DatosListadoCurso curso
) {
    public DatosListadoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),
                topico.isStatus(), new DatosListadoUsuario(topico.getUsuario()), new DatosListadoCurso(topico.getCurso()));
    }
}
