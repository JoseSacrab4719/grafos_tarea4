package gt.umg.prog3.tarea4.model;

import java.util.*;

/**
 * Grafo no dirigido y ponderado que representa una red de ciudades.
 */
public class GrafoRed {

    private final Map<String, List<arista>> adyacencia;
    private int totalAristas;

    public GrafoRed() {
        this.adyacencia = new LinkedHashMap<>();
        this.totalAristas = 0;
    }

    public void agregarCiudad(String ciudad) {
        if (adyacencia.containsKey(ciudad)) {
            throw new IllegalArgumentException("La ciudad '" + ciudad + "' ya existe en el grafo.");
        }
        adyacencia.put(ciudad, new ArrayList<>());
    }

    public void agregarCarretera(String origen, String destino, int distancia) {
        adyacencia.computeIfAbsent(origen, k -> new ArrayList<>());
        adyacencia.computeIfAbsent(destino, k -> new ArrayList<>());
        adyacencia.get(origen).add(new arista(origen, destino, distancia));
        adyacencia.get(destino).add(new arista(destino, origen, distancia));
        totalAristas++;
    }

    public List<arista> obtenerVecinos(String ciudad) {
        return adyacencia.getOrDefault(ciudad, Collections.emptyList());
    }

    public Set<String> obtenerCiudades() {
        return adyacencia.keySet();
    }

    public boolean existeCiudad(String ciudad) {
        return adyacencia.containsKey(ciudad);
    }

    public int totalCiudades() {
        return adyacencia.size();
    }

    public int totalAristas() {
        return totalAristas;
    }

    public Map<String, List<arista>> getAdyacencia() {
        return Collections.unmodifiableMap(adyacencia);
    }

    public int gradoCiudad(String ciudad) {
        return adyacencia.getOrDefault(ciudad, Collections.emptyList()).size();
    }
}
