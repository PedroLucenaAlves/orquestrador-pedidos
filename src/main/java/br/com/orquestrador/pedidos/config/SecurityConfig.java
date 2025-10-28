package br.com.orquestrador.pedidos.config;

import br.com.orquestrador.pedidos.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/hello").permitAll()
                        .requestMatchers("/v1/**").authenticated()
                )
                //Adicionar meu filtro antes do filtro padrao de username/password para garantir que a logica do jwt rode primeiro
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }




    //COM BASICH AUTH
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http
//                    .csrf(csrf -> csrf.disable()) // desativa CSRF (facilita no Postman)
//                    .authorizeHttpRequests(auth -> auth
//                            // ibera o endpoint público
//                            .requestMatchers("/v1/hello").permitAll()
//
//                            // exige autenticação no de todos endpoints do v1 (exceto o hello)
//                            .requestMatchers("/v1/**").authenticated()
//
//                            // bloqueia qualquer outro endpoint não definido
//                            .anyRequest().denyAll()
//                    )
//                    //usa autenticação básica (ideal para APIs e Postman)
//                    .httpBasic(Customizer.withDefaults());
//
//            return http.build();
//        }
//
//        //criando um usuario personalizado para o Spring de autenticacao (no caso para ser utilizado em Basic Auth)
//        @Bean
//        public UserDetailsService userDetailsService() {
//            UserDetails user = User
//                    .withDefaultPasswordEncoder()
//                    .username("admin")
//                    .password("12345")
//                    .roles("USER")
//                    .build();
//
//            return new InMemoryUserDetailsManager(user);
//        }
    }

