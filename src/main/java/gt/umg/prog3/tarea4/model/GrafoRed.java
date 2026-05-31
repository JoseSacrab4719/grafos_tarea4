package gt.umg.prog3.tarea4.model;

import java.util.*;

/**
 * Grafo no dirigido y ponderado representado con lista de adyacencia.
 * Cada ciudad es un vértice y cada carretera es una arista con distancia en km.
 *
 * @author JoseSacrab4719
 * @version 1.0
 */
public class GrafoRed {

    private final Map<String, List<Aristas>> adyacencia;
    private int totalAristas;

    /**
     * Constructor que inicializa el grafo vacío.
     */
    public GrafoRed() {
        this.adyacencia = new LinkedHashMap<>();
        this.totalAristas = 0;
    }

    /**
     * Agrega una nueva ciudad como vértice aislado.
     *
     * @param ciudad el nombre de la ciudad
     * @throws IllegalArgumentException si la ciudad ya existe
     */
    public void agregarCiudad(String ciudad) {
        if (adyacencia.containsKey(ciudad))
            throw new IllegalArgumentException("La ciudad '" + ciudad + "' ya existe en el grafo.");
        adyacencia.put(ciudad, new ArrayList<>());
    }

    /**
     * Agrega una carretera bidireccional ponderada entre dos ciudades.
     *
     * @param origen    ciudad de origen
     * @param destino   ciudad de destino
     * @param distancia distancia en kilómetros
     */
    public void agregarCarretera(String origen, String destino, int distancia) {
        adyacencia.computeIfAbsent(origen, k -> new ArrayList<>());
        adyacencia.computeIfAbsent(destino, k -> new ArrayList<>());
        adyacencia.get(origen).add(new Aristas(origen, destino, distancia));
        adyacencia.get(destino).add(new Aristas(destino, origen, distancia));
        totalAristas++;
    }

    /**
     * Retorna las aristas adyacentes a una ciudad.
     *
     * @param ciudad el nombre de la ciudad
     * @return lista de aristas salientes; vacía si no existe
     */
    public List<Aristas> obtenerVecinos(String ciudad) {
        return adyacencia.getOrDefault(ciudad, Collections.emptyList());
    }

    /**
     * Retorna el conjunto de todas las ciudades.
     * @return conjunto de nombres de ciudades
     */
    public Set<String> obtenerCiudades() { return adyacencia.keySet(); }

    /**
     * Verifica si una ciudad existe en el grafo.
     * @param ciudad el nombre a verificar
     * @return {@code true} si existe
     */
    public boolean existeCiudad(String ciudad) { return adyacencia.containsKey(ciudad); }

    /**
     * Retorna el total de ciudades (vértices).
     * @return número de ciudades
     */
    public int totalCiudades() { return adyacencia.size(); }

    /**
     * Retorna el total de aristas lógicas.
     * @return número de aristas
     */
    public int totalAristas() { return totalAristas; }

    /**
     * Retorna el mapa de adyacencia en modo solo lectura.
     * @return mapa no modificable
     */
    public Map<String, List<Aristas>> getAdyacencia() {
        return Collections.unmodifiableMap(adyacencia);
    }

    /**
     * Calcula el grado de una ciudad (cantidad de aristas incidentes).
     *
     * @param ciudad el nombre de la ciudad
     * @return grado del vértice
     */
    public int gradoCiudad(String ciudad) {
        return adyacencia.getOrDefault(ciudad, Collections.emptyList()).size();
    }
}
