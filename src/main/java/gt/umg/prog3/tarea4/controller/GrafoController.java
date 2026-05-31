package gt.umg.prog3.tarea4.controller;

import gt.umg.prog3.tarea4.dto.DTOs;
import gt.umg.prog3.tarea4.service.GrafoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grafos")
public class GrafoController {

    private final GrafoService grafoService;

    public GrafoController(GrafoService grafoService) {
        this.grafoService = grafoService;
    }

    @GetMapping("/red-ciudades")
    public ResponseEntity<DTOs.GrafoDTO> redCiudades() {
        return ResponseEntity.ok(grafoService.obtenerGrafoDTO());
    }

    @PostMapping("/ciudad")
    public ResponseEntity<String> agregarCiudad(@RequestParam String nombre) {
        grafoService.agregarCiudad(nombre);
        return ResponseEntity.ok("Ciudad '" + nombre + "' agregada correctamente.");
    }

    @GetMapping("/bfs")
    public ResponseEntity<DTOs.RecorridoDTO> bfs(@RequestParam String inicio) {
        return ResponseEntity.ok(grafoService.bfs(inicio));
    }

    @GetMapping("/dfs")
    public ResponseEntity<DTOs.RecorridoDTO> dfs(@RequestParam String inicio) {
        return ResponseEntity.ok(grafoService.dfs(inicio));
    }

    @GetMapping("/componentes-conexas")
    public ResponseEntity<DTOs.ComponentesDTO> componentesConexas() {
        return ResponseEntity.ok(grafoService.componentesConexas());
    }

    @GetMapping("/ruta-corta")
    public ResponseEntity<DTOs.RutaDTO> rutaMasCorta(@RequestParam String origen, @RequestParam String destino) {
        return ResponseEntity.ok(grafoService.rutaMasCorta(origen, destino));
    }

    @GetMapping("/distancias")
    public ResponseEntity<DTOs.DistanciasDTO> distancias(@RequestParam String origen) {
        return ResponseEntity.ok(grafoService.distanciasDesde(origen));
    }
}