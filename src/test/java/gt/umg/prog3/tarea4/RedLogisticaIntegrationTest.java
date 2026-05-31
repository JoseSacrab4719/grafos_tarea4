package gt.umg.prog3.tarea4;

import gt.umg.prog3.tarea4.dto.DTOs;
import gt.umg.prog3.tarea4.service.RedLogisticaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedLogisticaIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RedLogisticaService redService;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/red";
        try {
            redService.crearCentro(new DTOs.CentroRequestDTO("Centro Norte", "CN", "Norte"));
        } catch (IllegalArgumentException ignored) {}
        try {
            redService.crearCentro(new DTOs.CentroRequestDTO("Centro Sur", "CS", "Sur"));
        } catch (IllegalArgumentException ignored) {}
        try {
            redService.crearCentro(new DTOs.CentroRequestDTO("Centro Este", "CE", "Este"));
        } catch (IllegalArgumentException ignored) {}
        try {
            redService.agregarRuta(new DTOs.RutaRequestDTO("CN", "CS", 3));
        } catch (Exception ignored) {}
        try {
            redService.agregarRuta(new DTOs.RutaRequestDTO("CS", "CE", 2));
        } catch (Exception ignored) {}
    }

    @Test
    @DisplayName("IT1: POST centros debe retornar 201")
    void testCrearCentroRetorna201() {
        DTOs.CentroRequestDTO req = new DTOs.CentroRequestDTO("Centro Nuevo", "CNUEVO", "Occidente");
        ResponseEntity<String> resp = restTemplate.postForEntity(baseUrl + "/centros", req, String.class);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
    }

    @Test
    @DisplayName("IT2: POST con codigo duplicado debe retornar 400")
    void testCrearCentroDuplicadoRetorna400() {
        DTOs.CentroRequestDTO req = new DTOs.CentroRequestDTO("Duplicado", "CN", "Norte");
        ResponseEntity<String> resp = restTemplate.postForEntity(baseUrl + "/centros", req, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    @DisplayName("IT3: GET analisis debe retornar datos validos")
    void testAnalisisRetornaDatos() {
        ResponseEntity<DTOs.AnalisisRedDTO> resp =
            restTemplate.getForEntity(baseUrl + "/analisis", DTOs.AnalisisRedDTO.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertTrue(resp.getBody().getTotalCentros() >= 3);
    }

    @Test
    @DisplayName("IT4: GET ruta-optima debe retornar ruta")
    void testRutaOptimaRetornaRuta() {
        ResponseEntity<DTOs.RutaDTO> resp = restTemplate.getForEntity(
            baseUrl + "/ruta-optima?desde=CN&hasta=CE", DTOs.RutaDTO.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertFalse(resp.getBody().getRuta().isEmpty());
    }

    @Test
    @DisplayName("IT5: GET cobertura debe retornar centros alcanzables")
    void testCoberturaRetornaCentros() {
        ResponseEntity<DTOs.RecorridoDTO> resp = restTemplate.getForEntity(
            baseUrl + "/cobertura?desde=CN", DTOs.RecorridoDTO.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertTrue(resp.getBody().getTotalCiudadesVisitadas() >= 1);
    }

    @Test
    @DisplayName("IT6: GET centro-critico debe identificar el hub")
    void testCentroCriticoRetornaHub() {
        ResponseEntity<String> resp = restTemplate.getForEntity(
            baseUrl + "/centro-critico", String.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertFalse(resp.getBody().isBlank());
    }
}