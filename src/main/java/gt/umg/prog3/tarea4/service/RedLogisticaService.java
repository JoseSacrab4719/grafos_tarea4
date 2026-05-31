package gt.umg.prog3.tarea4.service;

import gt.umg.prog3.tarea4.dto.DTOs;
import gt.umg.prog3.tarea4.exception.CiudadNoEncontradaException;
import gt.umg.prog3.tarea4.model.CentroDistribucion;
import gt.umg.prog3.tarea4.model.GrafoRed;
import gt.umg.prog3.tarea4.model.arista;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RedLogisticaService {

    private final Map<String, CentroDistribucion> centros = new LinkedHashMap<>();
    private final GrafoRed redRutas = new GrafoRed();

    public CentroDistribucion crearCentro(DTOs.CentroRequestDTO req) {
        if (centros.containsKey(req.getCodigo()))
            throw new IllegalArgumentException("Ya existe un centro con código '" + req.getCodigo() + "'.");
        CentroDistribucion centro = new CentroDistribucion(req.getNombre(), req.getCodigo(), req.getRegion());
        centros.put(req.getCodigo(), centro);
        redRutas.agregarCiudad(req.getCodigo());
        return centro;
    }

    public void agregarRuta(DTOs.RutaRequestDTO req) {
        validarCodigo(req.getCodigoOrigen());
        validarCodigo(req.getCodigoDestino());
        redRutas.agregarCarretera(req.getCodigoOrigen(), req.getCodigoDestino(), req.getTiempoHoras());
    }

    public DTOs.RutaDTO rutaOptima(String desde, String hasta) {
        validarCodigo(desde);
        validarCodigo(hasta);
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> predecesor = new HashMap<>();
        Set<String> procesados = new HashSet<>();
        for (String c : redRutas.obtenerCiudades()) dist.put(c, Integer.MAX_VALUE);
        dist.put(desde, 0);
        PriorityQueue<AbstractMap.SimpleEntry<Integer, String>> pq =
            new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getKey));
        pq.offer(new AbstractMap.SimpleEntry<>(0, desde));
        while (!pq.isEmpty()) {
            var e = pq.poll();
            String u = e.getValue();
            if (procesados.contains(u)) continue;
            procesados.add(u);
            for (arista a : redRutas.obtenerVecinos(u)) {
                String v = a.getDestino();
                if (dist.get(u) != Integer.MAX_VALUE && dist.get(u) + a.getPeso() < dist.get(v)) {
                    dist.put(v, dist.get(u) + a.getPeso());
                    predecesor.put(v, u);
                    pq.offer(new AbstractMap.SimpleEntry<>(dist.get(v), v));
                }
            }
        }
        if (dist.get(hasta) == Integer.MAX_VALUE)
            return new DTOs.RutaDTO(desde, hasta, Collections.emptyList(), -1, "Sin ruta disponible.");
        List<String> ruta = new ArrayList<>();
        String actual = hasta;
        while (actual != null) { ruta.add(0, actual); actual = predecesor.get(actual); }
        return new DTOs.RutaDTO(desde, hasta, ruta, dist.get(hasta), "Ruta óptima: " + dist.get(hasta) + " horas.");
    }

    public DTOs.RecorridoDTO cobertura(String desde) {
        validarCodigo(desde);
        List<String> visitados = new ArrayList<>();
        Set<String> vistos = new LinkedHashSet<>();
        Queue<String> cola = new LinkedList<>();
        cola.offer(desde);
        vistos.add(desde);
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            visitados.add(actual);
            for (arista a : redRutas.obtenerVecinos(actual)) {
                if (!vistos.contains(a.getDestino())) {
                    vistos.add(a.getDestino());
                    cola.offer(a.getDestino());
                }
            }
        }
        return new DTOs.RecorridoDTO(desde, "BFS-Cobertura", visitados, visitados.size());
    }

    public String centroCritico() {
        String hub = null;
        int maxGrado = -1;
        for (String codigo : redRutas.obtenerCiudades()) {
            int grado = redRutas.gradoCiudad(codigo);
            if (grado > maxGrado) { maxGrado = grado; hub = codigo; }
        }
        return hub != null ? hub + " (grado: " + maxGrado + ")" : "Sin centros registrados";
    }

    public DTOs.AnalisisRedDTO analisis() {
        boolean esConexo = verificarConexo();
        String hubCodigo = "";
        int maxGrado = -1;
        for (String codigo : redRutas.obtenerCiudades()) {
            int g = redRutas.gradoCiudad(codigo);
            if (g > maxGrado) { maxGrado = g; hubCodigo = codigo; }
        }
        return new DTOs.AnalisisRedDTO(redRutas.totalCiudades(), redRutas.totalAristas(), esConexo, hubCodigo, maxGrado);
    }

    private boolean verificarConexo() {
        if (redRutas.totalCiudades() == 0) return true;
        Set<String> visitados = new HashSet<>();
        String inicio = redRutas.obtenerCiudades().iterator().next();
        dfsConexo(inicio, visitados);
        return visitados.size() == redRutas.totalCiudades();
    }

    private void dfsConexo(String vertice, Set<String> visitados) {
        visitados.add(vertice);
        for (arista a : redRutas.obtenerVecinos(vertice)) {
            if (!visitados.contains(a.getDestino())) dfsConexo(a.getDestino(), visitados);
        }
    }

    private void validarCodigo(String codigo) {
        if (!centros.containsKey(codigo)) throw new CiudadNoEncontradaException(codigo);
    }
}