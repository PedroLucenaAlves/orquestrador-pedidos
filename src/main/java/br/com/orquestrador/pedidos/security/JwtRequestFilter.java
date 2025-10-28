package br.com.orquestrador.pedidos.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Criando um filtro de autenticação para o Spring
 */

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Capturando o header Authorization
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        //Validando se o Header existe e se o valor esta correto comecando com "Bearer"
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7); // Extrai só o token
            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (Exception e) {
                // Token pode ser inválido, expirado, etc.
                logger.warn("Não foi possível extrair o username do token: " + e.getMessage());
            }
        }

        //Usernaname existente porem o usuario nao esta autenticado
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // . Validamos o token (esta é a segunda verificação, crucial)
            if (jwtUtil.validateToken(jwtToken)) {

                // . Criamos o "Ticket" de autenticação para o Spring
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>()); // Usamos ArrayList vazio pois não estamos gerenciando "Roles"

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // . Colocamos o usuário autenticado no Contexto de Segurança do Spring
                // A partir daqui, o Spring considera esta requisição como VÁLIDA.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // . Passa a requisição para o próximo filtro na cadeia
        filterChain.doFilter(request, response);
    }


}
