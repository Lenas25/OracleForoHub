package alura.cursos.forohub.infra.security;

import alura.cursos.forohub.entities.auth.UsuarioAuth;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String apiSecret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generarToken(UsuarioAuth usuario) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + expiration);
        try{
            // cambiar a variable de entorno
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            String token = JWT.create()
                    .withIssuer("forohub")
                    .withSubject(usuario.getCorreoElectronico())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(expiracion)
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException(exception);
        }
    }

    public String getSubject(String token){

        if(token == null){
            throw new RuntimeException("Token invalido");
        }

        DecodedJWT verifier = null;
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("forohub")
                    .build()
                    .verify(token);
        }catch (JWTCreationException exception){
            throw new RuntimeException(exception);
        }

        if(verifier.getSubject() == null){
            throw new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();
    }

}
