package alura.cursos.forohub.controller;

import alura.cursos.forohub.dto.security.DatosAuthUsuario;
import alura.cursos.forohub.dto.security.DatosJWTToken;
import alura.cursos.forohub.dto.security.Response;
import alura.cursos.forohub.dto.usuario.DatosRegistroUsuario;
import alura.cursos.forohub.entities.auth.UsuarioAuth;
import alura.cursos.forohub.entities.perfil.Perfil;
import alura.cursos.forohub.entities.perfil.PerfilRepository;
import alura.cursos.forohub.entities.usuario.Usuario;
import alura.cursos.forohub.entities.usuario.UsuarioRepository;
import alura.cursos.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAuthUsuario datos) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.correoElectronico(), datos.contrasena());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTToken = tokenService.generarToken((UsuarioAuth) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTToken));
    }

    @PostMapping("/signup")
    public ResponseEntity crearUsuario(@RequestBody @Valid DatosRegistroUsuario datos) {
        try {
            Optional<Usuario> userExisting = usuarioRepository.findFirstByCorreoElectronico(datos.correoElectronico());
            if (userExisting.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Usuario ya existe", userExisting));
            }
            Optional<Perfil> perfilExisting = perfilRepository.findFirstByNombre(datos.perfil().nombre());
            Perfil perfil = null;
            if (perfilExisting.isPresent()) {
                perfil = perfilExisting.get();
            }else{
                perfil = new Perfil(null, datos.perfil().nombre());
            }
            String contrasena = passwordEncoder.encode(datos.contrasena());
            var newUser = new Usuario(null, datos.nombre(), datos.correoElectronico(), contrasena, perfil);
            usuarioRepository.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Usuario registrado exitosamente", newUser));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error al registrar", e));
        }
    }
}
