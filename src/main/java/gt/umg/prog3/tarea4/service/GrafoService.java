package gt.umg.prog3.tarea4.service;

import gt.umg.prog3.tarea4.dto.DTOs;
import gt.umg.prog3.tarea4.exception.CiudadNoEncontradaException;
import gt.umg.prog3.tarea4.model.Aristas;
import gt.umg.prog3.tarea4.model.GrafoRed;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Servicio que gestiona el grafo de ciudades guatemaltecas.
 * Implementa BFS, DFS y Dijkstra sobre la red de carreteras.
 *
 * @author JoseSacrab4719
 * @version 1.0
 */
@Service
public class GrafoService {

    private final GrafoRed grafo;

    /**
     * Inicializa el grafo con 6 ciudades y 7 carreteras del enunciado.
     * Cobán se agrega como ciudad aislada (sin conexiones).
     */
    public GrafoService() {
        this.grafo = new GrafoRed();
        grafo.agregarCarretera("Guatemala",     "Antigua",       45);
        grafo.agregarCarretera("Guatemala",     "Chimaltenango", 54);
        grafo.agregarCarretera("Chimaltenango", "Antigua",       20);
        grafo.agregarCarretera("Guatemala",     "Escuintla",     60);
        grafo.agregarCarretera("Escuintla",     "Antigua",       52);
        grafo.agregarCarretera("Guatemala",     "Amatitlan",     28);
        grafo.agregarCarretera("Amatitlan",     "Escuintla",     38);
        grafo.agregarCiudad("Coban");
    }

    /**
     * Genera la representación DTO del grafo completo.
     *
     * @return {@link DTOs.GrafoDTO} con ciudades, aristas y adyacencia
     */
    public DTOs.GrafoDTO obtenerGrafoDTO() {
        Map<String, List<DTOs.AristaDTO>> red = new LinkedHashMap<>();
        for (String ciudad : grafo.obtenerCiudades()) {
            List<DTOs.AristaDTO> vecinos = new ArrayList<>();
            for (Aristas a : grafo.obtenerVecinos(ciudad))
                vecinos.add(new DTOs.AristaDTO(a.getDestino(), a.getPeso()));
            red.put(ciudad, vecinos);
        }
        return new DTOs.GrafoDTO(grafo.totalCiudades(), grafo.totalAristas(), red);
    }

    /**
     * Agrega una nueva ciudad al grafo.
     *
     * @param ciudad el nombre de la ciudad
     * @throws IllegalArgumentException si ya existe
     */
    public void agregarCiudad(String ciudad) { grafo.agregarCiudad(ciudad); }

    /**
     * Recorrido en anchura (BFS) desde una ciudad de inicio.
     *
     * @param ciudadInicio ciudad desde donde inicia el recorrido
     * @return {@link DTOs.RecorridoDTO} con orden de visita y total visitadas
     * @throws CiudadNoEncontradaException si la ciudad no existe
     */
    public DTOs.RecorridoDTO bfs(String ciudadInicio) {
        validarCiudad(ciudadInicio);
        List<String> ordenVisita = new ArrayList<>();
        Set<String> vistos = new LinkedHashSet<>();
        Queue<String> cola = new LinkedList<>();
        cola.offer(ciudadInicio);
        vistos.add(ciudadInicio);
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            ordenVisita.add(actual);
            for (Aristas vecino : grafo.obtenerVecinos(actual))
                if (!vistos.contains(vecino.getDestino())) {
                    vistos.add(vecino.getDestino());
                    cola.offer(vecino.getDestino());
                }
        }
        return new DTOs.RecorridoDTO(ciudadInicio, "BFS", ordenVisita, ordenVisita.size());
    }

    /**
     * Recorrido en profundidad (DFS) desde una ciudad de inicio.
     *
     * @param ciudadInicio ciudad desde donde inicia el recorrido
     * @return {@link DTOs.RecorridoDTO} con orden de visita y total visitadas
     * @throws CiudadNoEncontradaException si la ciudad no existe
     */
    public DTOs.RecorridoDTO dfs(String ciudadInicio) {
        validarCiudad(ciudadInicio);
        List<String> resultado = new ArrayList<>();
        dfsRecursivo(ciudadInicio, new LinkedHashSet<>(), resultado);
        return new DTOs.RecorridoDTO(ciudadInicio, "DFS", resultado, resultado.size());
    }

    /**
     * Método auxiliar recursivo para DFS.
     *
     * @param vertice   vértice actual
     * @param visitados conjunto de ya visitados
     * @param resultado lista acumulada del recorrido
     */
    private void dfsRecursivo(String vertice, Set<String> visitados, List<String> resultado) {
        visitados.add(vertice);
        resultado.add(vertice);
        for (Aristas vecino : grafo.obtenerVecinos(vertice))
            if (!visitados.contains(vecino.getDestino()))
                dfsRecursivo(vecino.getDestino(), visitados, resultado);
    }

    /**
     * Detecta todas las componentes conexas del grafo usando DFS.
     *
     * @return {@link DTOs.ComponentesDTO} con total y lista de componentes
     */
    public DTOs.ComponentesDTO componentesConexas() {
        Set<String> visitados = new HashSet<>();
        List<List<String>> componentes = new ArrayList<>();
        for (String ciudad : grafo.obtenerCiudades())
            if (!visitados.contains(ciudad)) {
                List<String> comp = new ArrayList<>();
                dfsRecursivo(ciudad, visitados, comp);
                componentes.add(comp);
            }
        return new DTOs.ComponentesDTO(componentes.size(), componentes);
    }

    /**
     * Ruta más corta entre dos ciudades usando Dijkstra.
     * Retorna distancia -1 si el destino es inalcanzable.
     *
     * @param origen  ciudad de origen
     * @param destino ciudad de destino
     * @return {@link DTOs.RutaDTO} con ruta, distancia y mensaje
     * @throws CiudadNoEncontradaException si alguna ciudad no existe
     */
    public DTOs.RutaDTO rutaMasCorta(String origen, String destino) {
        validarCiudad(origen);
        validarCiudad(destino);
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> predecesor = new HashMap<>();
        Set<String> procesados = new HashSet<>();
        for (String c : grafo.obtenerCiudades()) dist.put(c, Integer.MAX_VALUE);
        dist.put(origen, 0);
        PriorityQueue<AbstractMap.SimpleEntry<Integer, String>> pq =
            new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getKey));
        pq.offer(new AbstractMap.SimpleEntry<>(0, origen));
        while (!pq.isEmpty()) {
            var e = pq.poll();
            String u = e.getValue();
            if (procesados.contains(u)) continue;
            procesados.add(u);
            for (Aristas vecino : grafo.obtenerVecinos(u)) {
                String v = vecino.getDestino();
                if (dist.get(u) != Integer.MAX_VALUE && dist.get(u) + vecino.getPeso() < dist.get(v)) {
                    dist.put(v, dist.get(u) + vecino.getPeso());
                    predecesor.put(v, u);
                    pq.offer(new AbstractMap.SimpleEntry<>(dist.get(v), v));
                }
            }
        }
        if (dist.get(destino) == Integer.MAX_VALUE)
            return new DTOs.RutaDTO(origen, destino, Collections.emptyList(), -1,
                "Sin ruta disponible entre " + origen + " y " + destino);
        List<String> ruta = new ArrayList<>();
        String actual = destino;
        while (actual != null) { ruta.add(0, actual); actual = predecesor.get(actual); }
        return new DTOs.RutaDTO(origen, destino, ruta, dist.get(destino),
            "Ruta encontrada con distancia " + dist.get(destino) + " km");
    }

    /**
     * Distancias mínimas desde un origen a todas las ciudades (Dijkstra).
     * Ciudades inalcanzables se marcan con -1.
     *
     * @param origen ciudad de origen
     * @return {@link DTOs.DistanciasDTO} con el mapa de distancias
     * @throws CiudadNoEncontradaException si la ciudad no existe
     */
    public DTOs.DistanciasDTO distanciasDesde(String origen) {
        validarCiudad(origen);
        Map<String, Integer> dist = new HashMap<>();
        Set<String> procesados = new HashSet<>();
        for (String c : grafo.obtenerCiudades()) dist.put(c, Integer.MAX_VALUE);
        dist.put(origen, 0);
        PriorityQueue<AbstractMap.SimpleEntry<Integer, String>> pq =
            new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getKey));
        pq.offer(new AbstractMap.SimpleEntry<>(0, origen));
        while (!pq.isEmpty()) {
            var e = pq.poll();
            String u = e.getValue();
            if (procesados.contains(u)) continue;
            procesados.add(u);
            for (Aristas vecino : grafo.obtenerVecinos(u)) {
                String v = vecino.getDestino();
                if (dist.get(u) != Integer.MAX_VALUE && dist.get(u) + vecino.getPeso() < dist.get(v)) {
                    dist.put(v, dist.get(u) + vecino.getPeso());
                    pq.offer(new AbstractMap.SimpleEntry<>(dist.get(v), v));
                }
            }
        }
        Map<String, Integer> resultado = new LinkedHashMap<>();
        dist.forEach((k, v) -> resultado.put(k, v == Integer.MAX_VALUE ? -1 : v));
        return new DTOs.DistanciasDTO(origen, resultado);
    }

    /**
     * Valida que una ciudad exista en el grafo.
     *
     * @param ciudad nombre de la ciudad
     * @throws CiudadNoEncontradaException si no existe
     */
    private void validarCiudad(String ciudad) {
        if (!grafo.existeCiudad(ciudad)) throw new CiudadNoEncontradaException(ciudad);
    }

    /**
     * Retorna el grafo interno para uso en tests.
     * @return el {@link GrafoRed} interno
     */
    public GrafoRed getGrafo() { return grafo; }
}
