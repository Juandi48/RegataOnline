package co.edu.javeriana.regata.config;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.repository.JugadorRepository;
import co.edu.javeriana.regata.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                    .orElseThrow(() -> new UsernameNotFoundException("Jugador no encontrado: " + username));

            // j.getRol() = "ADMIN" o "JUGADOR"
            return User.withUsername(j.getEmail())
                    .password(j.getPassword())
                    .roles(j.getRol())   // genera ROLE_ADMIN / ROLE_JUGADOR
                    .build();
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            DaoAuthenticationProvider authProvider) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // H2 console
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        http.authenticationProvider(authProvider);

        http.authorizeHttpRequests(auth -> auth
                // ===== CORS / preflight =====
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ===== PÚBLICOS =====
                .requestMatchers("/h2/**").permitAll()
                .requestMatchers("/api/v1/auth/login", "/api/v1/auth/signup").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/modelos/**").permitAll()

                // ===== BARCOS: cualquier autenticado puede listar y crear =====
                .requestMatchers(HttpMethod.GET, "/api/v1/barcos/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/barcos/**").authenticated()
                // borrar barcos solo admin (si quieres):
                .requestMatchers(HttpMethod.DELETE, "/api/v1/barcos/**").hasRole("ADMIN")

                // ===== RESTO: solo autenticados =====
                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
