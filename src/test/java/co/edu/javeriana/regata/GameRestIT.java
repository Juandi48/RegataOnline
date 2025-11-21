package co.edu.javeriana.regata;

import co.edu.javeriana.regata.web.dto.MovimientoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameRestIT {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void testListarBarcosEnJuego() {
        ResponseEntity<String> res =
                rest.getForEntity("/api/v1/juego/barcos", String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testAplicarMovimiento() {
        MovimientoRequest req = new MovimientoRequest();
        req.setBarcoId(1L);
        req.setDeltaVx(1);
        req.setDeltaVy(0);

        ResponseEntity<String> res = rest.postForEntity(
                "/api/v1/juego/mover", req, String.class);

        assertThat(res.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }
}
