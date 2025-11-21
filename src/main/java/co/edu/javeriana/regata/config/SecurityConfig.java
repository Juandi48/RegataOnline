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

    // Cargar Jugador desde la BD usando el email como username
    @Bean
    public UserDetailsService userDetailsService(JugadorRepository jugadorRepository) {
        return username -> {
            Jugador j = jugadorRepository.findByEmail(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("Jugador no encontrado: " + username));

            // j.getRol() devuelve "ADMIN" o "JUGADOR"
            return User.withUsername(j.getEmail())
                    .password(j.getPassword())
                    .roles(j.getRol())   // genera ROLE_ADMIN o ROLE_JUGADOR
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        // Para que funcione la consola H2 en el navegador
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        http.authorizeHttpRequests(auth -> auth
                // H2 console sin autenticación
                .requestMatchers("/h2/**").permitAll()

                // --------- ENDPOINTS PÚBLICOS (para llenar combos, etc.) ----------
                // listar jugadores (para el combo de login)
                .requestMatchers(HttpMethod.GET, "/api/v1/jugadores/**").permitAll()
                // listar modelos de barco (jugador necesita verlos para elegir uno)
                .requestMatchers(HttpMethod.GET, "/api/v1/modelos-barcos/**").permitAll()
                // listar barcos (por ahora dejamos GET abierto; la app ya filtra por jugador)
                .requestMatchers(HttpMethod.GET, "/api/v1/barcos/**").permitAll()

                // --------- AUTENTICACIÓN ----------
                // endpoint que usamos para login desde Angular (Basic Auth)
                .requestMatchers("/api/v1/auth/**").authenticated()

                // --------- RESTO DE ENDPOINTS ----------
                // cualquier otra cosa requiere estar autenticado;
                // los permisos finos (solo ADMIN para CRUD) los manejamos con @PreAuthorize
                .anyRequest().authenticated()
        );

        // Usamos HTTP Basic; Angular envía Authorization: Basic ...
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
