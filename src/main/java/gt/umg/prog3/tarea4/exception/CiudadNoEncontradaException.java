/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.prog3.tarea4.exception;

public class CiudadNoEncontradaException extends RuntimeException {
    public CiudadNoEncontradaException(String ciudad) {
        super("La ciudad '" + ciudad + "' no existe en el grafo.");
    }
}



