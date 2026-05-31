package gt.umg.prog3.tarea4.util;

/**
 * Clase de utilidades estáticas para operaciones con grafos.
 * Proporciona métodos de validación y conversión usados en toda la aplicación.
 *
 * @author JoseSacrab4719
 * @version 1.0
 */
public class GrafoUtils {

    /**
     * Constructor privado para evitar instanciación de clase utilitaria.
     */
    private GrafoUtils() {}

    /**
     * Verifica si un peso de arista es válido (mayor que cero).
     *
     * @param peso el peso de la arista a verificar
     * @return {@code true} si el peso es mayor que cero
     */
    public static boolean esPesoValido(int peso) {
        return peso > 0;
    }

    /**
     * Verifica si un nombre de ciudad no es nulo ni vacío.
     *
     * @param nombre el nombre a verificar
     * @return {@code true} si el nombre es válido
     */
    public static boolean esNombreValido(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    /**
     * Formatea una distancia en kilómetros como texto legible.
     *
     * @param km la distancia en kilómetros
     * @return cadena con formato "X km"
     */
    public static String formatearDistancia(int km) {
        return km + " km";
    }

    /**
     * Verifica si una distancia representa un destino inalcanzable.
     *
     * @param distancia la distancia a verificar
     * @return {@code true} si la distancia indica destino inalcanzable
     */
    public static boolean esInalcanzable(int distancia) {
        return distancia == -1 || distancia == Integer.MAX_VALUE;
    }
}