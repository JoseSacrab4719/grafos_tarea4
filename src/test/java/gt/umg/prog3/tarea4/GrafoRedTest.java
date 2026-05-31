package gt.umg.prog3.tarea4;

import gt.umg.prog3.tarea4.model.GrafoRed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link GrafoRed}.
 *
 * @author JoseSacrab4719
 * @version 1.0
 */
class GrafoRedTest {

    private GrafoRed grafo;

    /**
     * Inicializa el grafo con 6 ciudades y 7 aristas antes de cada test.
     */
    @BeforeEach
    void setUp() {
        grafo = new GrafoRed();
        grafo.agregarCarretera("Guatemala",     "Antigua",       45);
        grafo.agregarCarretera("Guatemala",     "Chimaltenango", 54);
        grafo.agregarCarretera("Chimaltenango", "Antigua",       20);
        grafo.agregarCarretera("Guatemala",     "Escuintla",     60);
        grafo.agregarCarretera("Escuintla",     "Antigua",       52);
        grafo.agregarCarretera("Guatemala",     "Amatitlan",     28);
        grafo.agregarCarretera("Amatitlan",     "Escuintla",     38);
        grafo.agregarCiudad("Coban");
    }

    /** Verifica 6 ciudades y 7 aristas exactas. */
    @Test
    @DisplayName("El grafo debe tener exactamente 6 ciudades y 7 aristas")
    void testTotalCiudadesYAristas() {
        assertEquals(6, grafo.totalCiudades());
        assertEquals(7, grafo.totalAristas());
    }

    /** Verifica que ciudad duplicada lanza excepción. */
    @Test
    @DisplayName("No se deben permitir ciudades duplicadas")
    void testCiudadDuplicadaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
            () -> grafo.agregarCiudad("Guatemala"));
    }

    /** Verifica que el grafo sea bidireccional. */
    @Test
    @DisplayName("El grafo debe ser no dirigido")
    void testGrafoNoDirigido() {
        assertTrue(grafo.obtenerVecinos("Guatemala")
            .stream().anyMatch(a -> a.getDestino().equals("Antigua")));
        assertTrue(grafo.obtenerVecinos("Antigua")
            .stream().anyMatch(a -> a.getDestino().equals("Guatemala")));
    }

    /** Verifica que el peso Guatemala-Antigua sea 45 km. */
    @Test
    @DisplayName("Los pesos de aristas deben ser correctos")
    void testPesosAristas() {
        int peso = grafo.obtenerVecinos("Guatemala").stream()
            .filter(a -> a.getDestino().equals("Antigua"))
            .mapToInt(a -> a.getPeso()).findFirst().orElse(-1);
        assertEquals(45, peso);
    }
}