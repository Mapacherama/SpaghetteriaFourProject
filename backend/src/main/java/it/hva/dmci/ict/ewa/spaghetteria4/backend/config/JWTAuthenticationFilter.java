package it.hva.dmci.ict.ewa.spaghetteria4.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static it.hva.dmci.ict.ewa.spaghetteria4.backend.config.SecurityConstants.EXPIRATION_TIME;
import static it.hva.dmci.ict.ewa.spaghetteria4.backend.config.SecurityConstants.SECRET;

/**
 * Specifies and handles the endpoint for signing in using credentials. returns a JWT Token for further operations.
 *
 * @author Sam Toxopeus
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withClaim("role",((User) auth.getPrincipal()).getRole())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));

        String body = String.format("{ \"username\":\"%s\", \"role\":\"%s\", \"token\":\"%s\", \"disabled\":\"%s\" }",
                ((User) auth.getPrincipal()).getUsername(), ((User) auth.getPrincipal()).getRole(), token, !((User) auth.getPrincipal()).isEnabled());

        res.setContentType("application/json");
        res.getWriter().write(body);
        res.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException, ServletException {
        res.setContentType("application/json");
        res.getWriter().write("Invalid credentials.");
        res.getWriter().flush();
    }
}
