package co.edu.javeriana.regata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BarcoRestIT {

    @LocalServerPort
    int port;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testListarBarcos() {
        webTestClient
                .get()
                .uri("/api/v1/barcos")
                .exchange()
                .expectStatus().isOk();
    }
}
