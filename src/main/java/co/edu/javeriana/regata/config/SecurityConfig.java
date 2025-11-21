package co.edu.javeriana.regata.config;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.repository.JugadorRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cargar Jugador desde la base usando email como username
    @Bean
    public UserDetailsService userDetailsService(JugadorRepository jugadorRepository) {
        return username -> {
            Jugador j = jugadorRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Jugador no encontrado: " + username));

            return User.withUsername(j.getEmail())
                    .password(j.getPassword())
                    .roles(j.getRol())  // "ADMIN" o "JUGADOR"
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        // Para la consola H2
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        http.authorizeHttpRequests(auth -> auth
                // H2 sin auth
                .requestMatchers("/h2/**").permitAll()

                // 🔓 Lista de jugadores pública para el combo del login
                .requestMatchers(HttpMethod.GET, "/api/v1/jugadores").permitAll()

                // Endpoint de autenticación (se llama ya con Basic desde Angular)
                .requestMatchers("/api/v1/auth/**").authenticated()

                // CRUD jugadores solo ADMIN
                .requestMatchers(HttpMethod.POST,   "/api/v1/jugadores/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/v1/jugadores/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/jugadores/**").hasRole("ADMIN")

                // Ejemplo: modelos/barcos/mapas CRUD solo ADMIN (puedes afinar GET si quieres)
                .requestMatchers(HttpMethod.POST,   "/api/v1/modelos-barcos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/v1/modelos-barcos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/modelos-barcos/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST,   "/api/v1/barcos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/v1/barcos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/barcos/**").hasRole("ADMIN")

                // El resto de cosas (juego, etc.) necesitan estar autenticadas (ADMIN o JUGADOR)
                .anyRequest().authenticated()
        );

        // HTTP Basic
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
