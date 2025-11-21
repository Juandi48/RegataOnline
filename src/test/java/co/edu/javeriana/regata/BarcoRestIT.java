package co.edu.javeriana.regata;

import co.edu.javeriana.regata.domain.Barco;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BarcoRestIT {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void testListarBarcos() {
        ResponseEntity<Barco[]> res =
                rest.getForEntity("/api/v1/barcos", Barco[].class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
