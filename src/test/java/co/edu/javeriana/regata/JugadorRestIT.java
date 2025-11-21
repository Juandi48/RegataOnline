package co.edu.javeriana.regata;

import co.edu.javeriana.regata.domain.Jugador;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JugadorRestIT {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void testListarJugadores() {
        ResponseEntity<Jugador[]> res = rest.getForEntity("/api/v1/jugadores", Jugador[].class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody()).isNotNull();
    }
}
