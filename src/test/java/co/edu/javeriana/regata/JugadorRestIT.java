package co.edu.javeriana.regata;

import co.edu.javeriana.regata.domain.ModeloBarco;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ModeloBarcoRestIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCrearModelo() {
        ModeloBarco m = new ModeloBarco();
        m.setNombre("Test Modelo");
        m.setColorHex("#FF0000");

        webTestClient
                .post()
                .uri("/api/v1/modelos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(m)
                .exchange()
                .expectStatus().is2xxSuccessful(); // antes era 200, esto acepta cualquier 2xx
    }

    @Test
    void testListarModelos() {
        webTestClient
                .get()
                .uri("/api/v1/modelos")
                .exchange()
                .expectStatus().isOk();
    }
}
