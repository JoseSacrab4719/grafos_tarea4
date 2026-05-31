package gt.umg.prog3.tarea4.controller;

import gt.umg.prog3.tarea4.dto.DTOs;
import gt.umg.prog3.tarea4.model.CentroDistribucion;
import gt.umg.prog3.tarea4.service.RedLogisticaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/red")
public class RedLogisticaController {

    private final RedLogisticaService redService;

    public RedLogisticaController(RedLogisticaService redService) {
        this.redService = redService;
    }

    @PostMapping("/centros")
    public ResponseEntity<CentroDistribucion> crearCentro(@RequestBody DTOs.CentroRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(redService.crearCentro(req));
    }

    @PostMapping("/rutas")
    public ResponseEntity<String> agregarRuta(@RequestBody DTOs.RutaRequestDTO req) {
        redService.agregarRuta(req);
        return ResponseEntity.ok("Ruta agregada entre " + req.getCodigoOrigen() + " y " + req.getCodigoDestino());
    }

    @GetMapping("/ruta-optima")
    public ResponseEntity<DTOs.RutaDTO> rutaOptima(@RequestParam String desde, @RequestParam String hasta) {
        return ResponseEntity.ok(redService.rutaOptima(desde, hasta));
    }

    @GetMapping("/cobertura")
    public ResponseEntity<DTOs.RecorridoDTO> cobertura(@RequestParam String desde) {
        return ResponseEntity.ok(redService.cobertura(desde));
    }

    @GetMapping("/centro-critico")
    public ResponseEntity<String> centroCritico() {
        return ResponseEntity.ok(redService.centroCritico());
    }

    @GetMapping("/analisis")
    public ResponseEntity<DTOs.AnalisisRedDTO> analisis() {
        return ResponseEntity.ok(redService.analisis());
    }
}
