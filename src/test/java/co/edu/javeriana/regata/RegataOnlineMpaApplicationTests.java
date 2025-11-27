package co.edu.javeriana.regata;

import co.edu.javeriana.regata.domain.ModeloBarco;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RegataOnlineMpaApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    /**
     * Prueba de sistema:
     * 1) Crea un Modelo de Barco vía REST.
     * 2) Lista todos los modelos.
     * 3) Verifica que el modelo creado aparece en el listado.
     */
    @Test
    void escenarioCompleto_crearYListarModelos() {
        String nombreModelo = "SistemaTest-Modelo";
        String colorHex = "#00FF00";

        // 1. Crear modelo
        ModeloBarco nuevo = new ModeloBarco();
        nuevo.setNombre(nombreModelo);
        nuevo.setColorHex(colorHex);

        webTestClient
                .post()
                .uri("/api/v1/modelos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(nuevo)
                .exchange()
                .expectStatus().is2xxSuccessful();

        // 2. Listar modelos y 3. verificar que el nuevo existe
        webTestClient
                .get()
                .uri("/api/v1/modelos")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ModeloBarco[].class)
                .value(modelos -> {
                    boolean existe = Arrays.stream(modelos)
                            .anyMatch(m -> nombreModelo.equals(m.getNombre()));
                    assertThat(existe).isTrue();
                });
    }
}
