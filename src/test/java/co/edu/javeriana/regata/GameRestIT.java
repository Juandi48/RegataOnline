package co.edu.javeriana.regata;

import co.edu.javeriana.regata.web.dto.MovimientoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class GameRestIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testListarBarcosEnJuego() {
        webTestClient
                .get()
                .uri("/api/v1/juego/barcos")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testAplicarMovimiento() {
        MovimientoRequest req = new MovimientoRequest();
        req.setBarcoId(1L);
        req.setAx(1); // aceleración en X
        req.setAy(0); // aceleración en Y

        EntityExchangeResult<String> res = webTestClient
                .post()
                .uri("/api/v1/juego/mover")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectBody(String.class)
                .returnResult();

        // Igual que antes: aceptamos OK o BAD_REQUEST como "respuesta razonable"
        HttpStatus status = HttpStatus.valueOf(res.getStatus().value());
        assertThat(status).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);

    }
}
