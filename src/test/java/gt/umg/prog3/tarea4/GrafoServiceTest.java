package gt.umg.prog3.tarea4;

import gt.umg.prog3.tarea4.dto.DTOs;
import gt.umg.prog3.tarea4.exception.CiudadNoEncontradaException;
import gt.umg.prog3.tarea4.service.GrafoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GrafoServiceTest {

    private GrafoService service;

    @BeforeEach
    void setUp() {
        service = new GrafoService();
    }

    @Test
    @DisplayName("BFS desde Guatemala debe iniciar con Guatemala")
    void testBfsIniciaCiudadCorrectamente() {
        DTOs.RecorridoDTO resultado = service.bfs("Guatemala");
        assertEquals("Guatemala", resultado.getOrdenVisita().get(0));
    }

    @Test
    @DisplayName("BFS desde Guatemala debe visitar 5 ciudades")
    void testBfsTotalCiudades() {
        DTOs.RecorridoDTO resultado = service.bfs("Guatemala");
        assertEquals(5, resultado.getTotalCiudadesVisitadas());
    }

    @Test
    @DisplayName("BFS con ciudad inexistente lanza excepcion")
    void testBfsCiudadInexistente() {
        assertThrows(CiudadNoEncontradaException.class,
            () -> service.bfs("CiudadFalsa"));
    }

    @Test
    @DisplayName("DFS desde Guatemala debe iniciar con Guatemala")
    void testDfsIniciaCiudadCorrectamente() {
        DTOs.RecorridoDTO resultado = service.dfs("Guatemala");
        assertEquals("Guatemala", resultado.getOrdenVisita().get(0));
    }

    @Test
    @DisplayName("DFS debe visitar 5 ciudades")
    void testDfsTotalCiudades() {
        DTOs.RecorridoDTO resultado = service.dfs("Guatemala");
        assertEquals(5, resultado.getTotalCiudadesVisitadas());
    }

    @Test
    @DisplayName("DFS debe contener todas las ciudades conectadas")
    void testDfsContieneTodasLasCiudades() {
        DTOs.RecorridoDTO resultado = service.dfs("Guatemala");
        List<String> orden = resultado.getOrdenVisita();
        assertTrue(orden.contains("Antigua"));
        assertTrue(orden.contains("Chimaltenango"));
        assertTrue(orden.contains("Escuintla"));
        assertTrue(orden.contains("Amatitlan"));
    }

    @Test
    @DisplayName("Dijkstra: ruta directa Guatemala a Antigua debe ser 45 km")
    void testRutaDirecta() {
        DTOs.RutaDTO ruta = service.rutaMasCorta("Guatemala", "Antigua");
        assertEquals(45, ruta.getDistanciaTotalKm());
    }

    @Test
    @DisplayName("Dijkstra: Amatitlan a Antigua debe ser 73 km")
    void testRutaConEscala() {
        DTOs.RutaDTO ruta = service.rutaMasCorta("Amatitlan", "Antigua");
        assertEquals(73, ruta.getDistanciaTotalKm());
    }

    @Test
    @DisplayName("Dijkstra: Coban aislada debe retornar sin ruta")
    void testDestinoInalcanzable() {
        DTOs.RutaDTO ruta = service.rutaMasCorta("Guatemala", "Coban");
        assertEquals(-1, ruta.getDistanciaTotalKm());
        assertTrue(ruta.getRuta().isEmpty());
    }

    @Test
    @DisplayName("El grafo debe tener 2 componentes conexas")
    void testComponentesConexas() {
        DTOs.ComponentesDTO comp = service.componentesConexas();
        assertEquals(2, comp.getTotalComponentes());
    }
}
