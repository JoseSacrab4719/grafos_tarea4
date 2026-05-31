package gt.umg.prog3.tarea4.exception;

import gt.umg.prog3.tarea4.dto.DTOs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CiudadNoEncontradaException.class)
    public ResponseEntity<DTOs.ErrorDTO> handleCiudadNoEncontrada(CiudadNoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new DTOs.ErrorDTO(404, ex.getMessage(), "Verifique el nombre de la ciudad."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DTOs.ErrorDTO> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new DTOs.ErrorDTO(400, ex.getMessage(), "Solicitud inválida."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DTOs.ErrorDTO> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new DTOs.ErrorDTO(500, "Error interno del servidor.", ex.getMessage()));
    }
}
