package gt.umg.prog3.tarea4.controller;

import gt.umg.prog3.tarea4.dto.DTOs;
import gt.umg.prog3.tarea4.service.GrafoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para el grafo de ciudades de Guatemala.
 * Expone endpoints para BFS, DFS, Dijkstra y componentes conexas.
 *
 * @author JoseSacrab4719
 * @version 1.0
 */
@RestController
@RequestMapping("/api/grafos")
public class GrafoController {

    private final GrafoService grafoService;

    /**
     * Constructor con inyección del servicio de grafos.
     * @param grafoService el servicio inyectado
     */
    public GrafoController(GrafoService grafoService) {
        this.grafoService = grafoService;
    }

    /**
     * Retorna el grafo completo en formato JSON.
     * @return grafo con ciudades y aristas, HTTP 200
     */
    @GetMapping("/red-ciudades")
    public ResponseEntity<DTOs.GrafoDTO> redCiudades() {
        return ResponseEntity.ok(grafoService.obtenerGrafoDTO());
    }

    /**
     * Agrega una ciudad al grafo. Retorna HTTP 400 si ya existe.
     * @param nombre nombre de la ciudad (query param)
     * @return mensaje de confirmación, HTTP 200
     */
    @PostMapping("/ciudad")
    public ResponseEntity<String> agregarCiudad(@RequestParam String nombre) {
        grafoService.agregarCiudad(nombre);
        return ResponseEntity.ok("Ciudad '" + nombre + "' agregada correctamente.");
    }

    /**
     * Recorrido BFS desde la ciudad indicada.
     * @param inicio ciudad de inicio (query param)
     * @return orden de visita y total, HTTP 200; HTTP 404 si no existe
     */
    @GetMapping("/bfs")
    public ResponseEntity<DTOs.RecorridoDTO> bfs(@RequestParam String inicio) {
        return ResponseEntity.ok(grafoService.bfs(inicio));
    }

    /**
     * Recorrido DFS desde la ciudad indicada.
     * @param inicio ciudad de inicio (query param)
     * @return orden de visita y total, HTTP 200; HTTP 404 si no existe
     */
    @GetMapping("/dfs")
    public ResponseEntity<DTOs.RecorridoDTO> dfs(@RequestParam String inicio) {
        return ResponseEntity.ok(grafoService.dfs(inicio));
    }

    /**
     * Retorna todas las componentes conexas del grafo.
     * @return lista de componentes, HTTP 200
     */
    @GetMapping("/componentes-conexas")
    public ResponseEntity<DTOs.ComponentesDTO> componentesConexas() {
        return ResponseEntity.ok(grafoService.componentesConexas());
    }

    /**
     * Ruta más corta entre dos ciudades (Dijkstra).
     * @param origen  ciudad de origen (query param)
     * @param destino ciudad de destino (query param)
     * @return ruta y distancia, HTTP 200; HTTP 404 si no existe alguna ciudad
     */
    @GetMapping("/ruta-corta")
    public ResponseEntity<DTOs.RutaDTO> rutaMasCorta(
            @RequestParam String origen,
            @RequestParam String destino) {
        return ResponseEntity.ok(grafoService.rutaMasCorta(origen, destino));
    }

    /**
     * Distancias mínimas desde un origen a todas las ciudades.
     * @param origen ciudad de origen (query param)
     * @return mapa de distancias, HTTP 200
     */
    @GetMapping("/distancias")
    public ResponseEntity<DTOs.DistanciasDTO> distancias(@RequestParam String origen) {
        return ResponseEntity.ok(grafoService.distanciasDesde(origen));
    }
}