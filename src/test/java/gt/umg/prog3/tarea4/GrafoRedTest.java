package gt.umg.prog3.tarea4;

import gt.umg.prog3.tarea4.model.GrafoRed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GrafoRedTest {

    private GrafoRed grafo;


    @Test
    @DisplayName("El grafo debe tener exactamente 6 ciudades y 7 aristas")
    void testTotalCiudadesYAristas() {
        assertEquals(6, grafo.totalCiudades(), "Debe haber 6 ciudades");
        assertEquals(7, grafo.totalAristas(),  "Debe haber 7 aristas");
    }

    @Test
    @DisplayName("No se deben permitir ciudades duplicadas")
    void testCiudadDuplicadaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
            () -> grafo.agregarCiudad("Guatemala"));
    }

    @Test
    @DisplayName("El grafo debe ser no dirigido")
    void testGrafoNoDirigido() {
        boolean guatemalaVeAntigua = grafo.obtenerVecinos("Guatemala")
            .stream().anyMatch(a -> a.getDestino().equals("Antigua"));
        boolean antiguaVeGuatemala = grafo.obtenerVecinos("Antigua")
            .stream().anyMatch(a -> a.getDestino().equals("Guatemala"));
        assertTrue(guatemalaVeAntigua);
        assertTrue(antiguaVeGuatemala);
    }

    @Test
    @DisplayName("Los pesos de aristas deben ser correctos")
    void testPesosAristas() {
        int peso = grafo.obtenerVecinos("Guatemala")
            .stream()
            .filter(a -> a.getDestino().equals("Antigua"))
            .mapToInt(a -> a.getPeso())
            .findFirst()
            .orElse(-1);
        assertEquals(45, peso);
    }
}