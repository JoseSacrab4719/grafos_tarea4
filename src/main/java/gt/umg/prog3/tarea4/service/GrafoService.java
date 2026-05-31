package gt.umg.prog3.tarea4.service;

import gt.umg.prog3.tarea4.dto.DTOs;
import gt.umg.prog3.tarea4.exception.CiudadNoEncontradaException;
import gt.umg.prog3.tarea4.model.GrafoRed;
import gt.umg.prog3.tarea4.model.arista;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrafoService {

    private final GrafoRed grafo;

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

    public DTOs.GrafoDTO obtenerGrafoDTO() {
        Map<String, List<DTOs.AristaDTO>> red = new LinkedHashMap<>();
        for (String ciudad : grafo.obtenerCiudades()) {
            List<DTOs.AristaDTO> vecinos = new ArrayList<>();
            for (arista a : grafo.obtenerVecinos(ciudad)) {
                vecinos.add(new DTOs.AristaDTO(a.getDestino(), a.getPeso()));
            }
            red.put(ciudad, vecinos);
        }
        return new DTOs.GrafoDTO(grafo.totalCiudades(), grafo.totalAristas(), red);
    }

    public void agregarCiudad(String ciudad) {
        grafo.agregarCiudad(ciudad);
    }

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
            for (arista vecino : grafo.obtenerVecinos(actual)) {
                if (!vistos.contains(vecino.getDestino())) {
                    vistos.add(vecino.getDestino());
                    cola.offer(vecino.getDestino());
                }
            }
        }
        return new DTOs.RecorridoDTO(ciudadInicio, "BFS", ordenVisita, ordenVisita.size());
    }

    public DTOs.RecorridoDTO dfs(String ciudadInicio) {
        validarCiudad(ciudadInicio);
        List<String> resultado = new ArrayList<>();
        dfsRecursivo(ciudadInicio, new LinkedHashSet<>(), resultado);
        return new DTOs.RecorridoDTO(ciudadInicio, "DFS", resultado, resultado.size());
    }

    private void dfsRecursivo(String vertice, Set<String> visitados, List<String> resultado) {
        visitados.add(vertice);
        resultado.add(vertice);
        for (arista vecino : grafo.obtenerVecinos(vertice)) {
            if (!visitados.contains(vecino.getDestino())) {
                dfsRecursivo(vecino.getDestino(), visitados, resultado);
            }
        }
    }

    public DTOs.ComponentesDTO componentesConexas() {
        Set<String> visitados = new HashSet<>();
        List<List<String>> componentes = new ArrayList<>();
        for (String ciudad : grafo.obtenerCiudades()) {
            if (!visitados.contains(ciudad)) {
                List<String> componente = new ArrayList<>();
                dfsRecursivo(ciudad, visitados, componente);
                componentes.add(componente);
            }
        }
        return new DTOs.ComponentesDTO(componentes.size(), componentes);
    }

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
            for (arista vecino : grafo.obtenerVecinos(u)) {
                String v = vecino.getDestino();
                int peso = vecino.getPeso();
                if (dist.get(u) != Integer.MAX_VALUE && dist.get(u) + peso < dist.get(v)) {
                    dist.put(v, dist.get(u) + peso);
                    predecesor.put(v, u);
                    pq.offer(new AbstractMap.SimpleEntry<>(dist.get(v), v));
                }
            }
        }
        if (dist.get(destino) == Integer.MAX_VALUE) {
            return new DTOs.RutaDTO(origen, destino, Collections.emptyList(), -1,
                "Sin ruta disponible entre " + origen + " y " + destino);
        }
        List<String> ruta = new ArrayList<>();
        String actual = destino;
        while (actual != null) { ruta.add(0, actual); actual = predecesor.get(actual); }
        return new DTOs.RutaDTO(origen, destino, ruta, dist.get(destino),
            "Ruta encontrada con distancia " + dist.get(destino) + " km");
    }

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
            for (arista vecino : grafo.obtenerVecinos(u)) {
                String v = vecino.getDestino();
                if (dist.get(u) != Integer.MAX_VALUE && dist.get(u) + vecino.getPeso() < dist.get(v)) {
                    dist.put(v, dist.get(u) + vecino.getPeso());
                    pq.offer(new AbstractMap.SimpleEntry<>(dist.get(v), v));
                }
            }
        }
        Map<String, Integer> resultado = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : dist.entrySet()) {
            resultado.put(entry.getKey(), entry.getValue() == Integer.MAX_VALUE ? -1 : entry.getValue());
        }
        return new DTOs.DistanciasDTO(origen, resultado);
    }

    private void validarCiudad(String ciudad) {
        if (!grafo.existeCiudad(ciudad)) throw new CiudadNoEncontradaException(ciudad);
    }

    public GrafoRed getGrafo() { return grafo; }
}
