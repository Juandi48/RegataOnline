package co.edu.javeriana.regata;

import co.edu.javeriana.regata.domain.ModeloBarco;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ModeloBarcoRestIT {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void testCrearModelo() {
        ModeloBarco m = new ModeloBarco();
        m.setNombre("Test Modelo");
        m.setColorHex("#FF0000");

        ResponseEntity<ModeloBarco> res =
                rest.postForEntity("/api/v1/modelos", m, ModeloBarco.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testListarModelos() {
        ResponseEntity<ModeloBarco[]> res =
                rest.getForEntity("/api/v1/modelos", ModeloBarco[].class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
