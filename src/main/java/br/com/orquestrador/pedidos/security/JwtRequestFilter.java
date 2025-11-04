package br.com.orquestrador.pedidos.security;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Intercepta cada requisição e verifica o token
 */

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper; //escreve o json do erro

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JwtRequestFilter.class);

    //setamos uma verificacao personalizada em cima da implementacao da interface OncePerRequestFilter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Capturando o header Authorization
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        //Validando se o Header existe e se o valor esta correto comecando com "Bearer"
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7); // Extrai só o token (discarta 0s 7 primeiros valores "Bearer "
            try {
                username = jwtUtil.getUsernameFromToken(jwtToken); //le o usuario de dentro do token
            } catch (Exception e) {
                // Token pode ser inválido, expirado, etc.
                logger.warn("Não foi possível extrair o username do token: {}", e.getMessage());

                //Montamos o JSON do erro correto para o try-catch nao mascarar o erro
                Map<String, Object> body = new HashMap<>();
                body.put("status", HttpServletResponse.SC_UNAUTHORIZED); // 401
                body.put("erro", "Não Autorizado");
                body.put("mensagem", "Token inválido, expirado ou com assinatura incorreta."); // A mensagem do Teste 3!
                body.put("caminho", request.getRequestURI());

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");

                objectMapper.writeValue(response.getWriter(), body);

                //para a execucação do filtro impedindo de passar para o proximo com o doFIlter e devolve a resposta correta 401 com nosso json
                return;

            }
        }

        //valida a leitura do nome do usuario e se este usuario ja esta autenticado (no nosso caso ainda nao esta autenticado)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // . Validamos o token (esta é a segunda verificação, crucial)
            if (jwtUtil.validateToken(jwtToken)) {

                // . Criamos o "Ticket" de autenticação para o Spring
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()); //nao possui senha nem regras especiais

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // . Colocamos o usuário autenticado no Contexto de Segurança do Spring
                // A partir daqui, o Spring considera esta requisição como VÁLIDA.
                SecurityContextHolder.getContext().setAuthentication(authToken); //este trecho que faz as informacoes que exigem token serem liberadas
            }
        }

        // . Passa a requisição para o próximo filtro na cadeia
        filterChain.doFilter(request, response);
    }


}
