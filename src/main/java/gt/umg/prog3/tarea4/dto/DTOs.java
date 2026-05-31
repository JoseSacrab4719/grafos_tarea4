package gt.umg.prog3.tarea4.dto;

import java.util.List;
import java.util.Map;

public class DTOs {

    public static class AristaDTO {
        private String destino;
        private int distanciaKm;
        public AristaDTO() {}
        public AristaDTO(String destino, int distanciaKm) { this.destino = destino; this.distanciaKm = distanciaKm; }
        public String getDestino() { return destino; }
        public void setDestino(String destino) { this.destino = destino; }
        public int getDistanciaKm() { return distanciaKm; }
        public void setDistanciaKm(int distanciaKm) { this.distanciaKm = distanciaKm; }
    }

    public static class GrafoDTO {
        private int totalCiudades;
        private int totalCarreteras;
        private Map<String, List<AristaDTO>> red;
        public GrafoDTO() {}
        public GrafoDTO(int totalCiudades, int totalCarreteras, Map<String, List<AristaDTO>> red) {
            this.totalCiudades = totalCiudades; this.totalCarreteras = totalCarreteras; this.red = red;
        }
        public int getTotalCiudades() { return totalCiudades; }
        public void setTotalCiudades(int v) { this.totalCiudades = v; }
        public int getTotalCarreteras() { return totalCarreteras; }
        public void setTotalCarreteras(int v) { this.totalCarreteras = v; }
        public Map<String, List<AristaDTO>> getRed() { return red; }
        public void setRed(Map<String, List<AristaDTO>> red) { this.red = red; }
    }

    public static class RecorridoDTO {
        private String ciudadInicio;
        private String tipoRecorrido;
        private List<String> ordenVisita;
        private int totalCiudadesVisitadas;
        public RecorridoDTO() {}
        public RecorridoDTO(String ciudadInicio, String tipoRecorrido, List<String> ordenVisita, int totalCiudadesVisitadas) {
            this.ciudadInicio = ciudadInicio; this.tipoRecorrido = tipoRecorrido;
            this.ordenVisita = ordenVisita; this.totalCiudadesVisitadas = totalCiudadesVisitadas;
        }
        public String getCiudadInicio() { return ciudadInicio; }
        public void setCiudadInicio(String v) { this.ciudadInicio = v; }
        public String getTipoRecorrido() { return tipoRecorrido; }
        public void setTipoRecorrido(String v) { this.tipoRecorrido = v; }
        public List<String> getOrdenVisita() { return ordenVisita; }
        public void setOrdenVisita(List<String> v) { this.ordenVisita = v; }
        public int getTotalCiudadesVisitadas() { return totalCiudadesVisitadas; }
        public void setTotalCiudadesVisitadas(int v) { this.totalCiudadesVisitadas = v; }
    }

    public static class ComponentesDTO {
        private int totalComponentes;
        private List<List<String>> componentes;
        public ComponentesDTO() {}
        public ComponentesDTO(int totalComponentes, List<List<String>> componentes) {
            this.totalComponentes = totalComponentes; this.componentes = componentes;
        }
        public int getTotalComponentes() { return totalComponentes; }
        public void setTotalComponentes(int v) { this.totalComponentes = v; }
        public List<List<String>> getComponentes() { return componentes; }
        public void setComponentes(List<List<String>> v) { this.componentes = v; }
    }

    public static class RutaDTO {
        private String origen;
        private String destino;
        private List<String> ruta;
        private int distanciaTotalKm;
        private String mensaje;
        public RutaDTO() {}
        public RutaDTO(String origen, String destino, List<String> ruta, int distanciaTotalKm, String mensaje) {
            this.origen = origen; this.destino = destino; this.ruta = ruta;
            this.distanciaTotalKm = distanciaTotalKm; this.mensaje = mensaje;
        }
        public String getOrigen() { return origen; }
        public void setOrigen(String v) { this.origen = v; }
        public String getDestino() { return destino; }
        public void setDestino(String v) { this.destino = v; }
        public List<String> getRuta() { return ruta; }
        public void setRuta(List<String> v) { this.ruta = v; }
        public int getDistanciaTotalKm() { return distanciaTotalKm; }
        public void setDistanciaTotalKm(int v) { this.distanciaTotalKm = v; }
        public String getMensaje() { return mensaje; }
        public void setMensaje(String v) { this.mensaje = v; }
    }

    public static class DistanciasDTO {
        private String origen;
        private Map<String, Integer> distancias;
        public DistanciasDTO() {}
        public DistanciasDTO(String origen, Map<String, Integer> distancias) {
            this.origen = origen; this.distancias = distancias;
        }
        public String getOrigen() { return origen; }
        public void setOrigen(String v) { this.origen = v; }
        public Map<String, Integer> getDistancias() { return distancias; }
        public void setDistancias(Map<String, Integer> v) { this.distancias = v; }
    }

    public static class CentroRequestDTO {
        private String nombre;
        private String codigo;
        private String region;
        public CentroRequestDTO() {}
        public CentroRequestDTO(String nombre, String codigo, String region) {
            this.nombre = nombre; this.codigo = codigo; this.region = region;
        }
        public String getNombre() { return nombre; }
        public void setNombre(String v) { this.nombre = v; }
        public String getCodigo() { return codigo; }
        public void setCodigo(String v) { this.codigo = v; }
        public String getRegion() { return region; }
        public void setRegion(String v) { this.region = v; }
    }

    public static class RutaRequestDTO {
        private String codigoOrigen;
        private String codigoDestino;
        private int tiempoHoras;
        public RutaRequestDTO() {}
        public RutaRequestDTO(String codigoOrigen, String codigoDestino, int tiempoHoras) {
            this.codigoOrigen = codigoOrigen; this.codigoDestino = codigoDestino; this.tiempoHoras = tiempoHoras;
        }
        public String getCodigoOrigen() { return codigoOrigen; }
        public void setCodigoOrigen(String v) { this.codigoOrigen = v; }
        public String getCodigoDestino() { return codigoDestino; }
        public void setCodigoDestino(String v) { this.codigoDestino = v; }
        public int getTiempoHoras() { return tiempoHoras; }
        public void setTiempoHoras(int v) { this.tiempoHoras = v; }
    }

    public static class AnalisisRedDTO {
        private int totalCentros;
        private int totalRutas;
        private boolean esConexo;
        private String centroMasConectado;
        private int gradoMaximo;
        public AnalisisRedDTO() {}
        public AnalisisRedDTO(int totalCentros, int totalRutas, boolean esConexo, String centroMasConectado, int gradoMaximo) {
            this.totalCentros = totalCentros; this.totalRutas = totalRutas; this.esConexo = esConexo;
            this.centroMasConectado = centroMasConectado; this.gradoMaximo = gradoMaximo;
        }
        public int getTotalCentros() { return totalCentros; }
        public void setTotalCentros(int v) { this.totalCentros = v; }
        public int getTotalRutas() { return totalRutas; }
        public void setTotalRutas(int v) { this.totalRutas = v; }
        public boolean isEsConexo() { return esConexo; }
        public void setEsConexo(boolean v) { this.esConexo = v; }
        public String getCentroMasConectado() { return centroMasConectado; }
        public void setCentroMasConectado(String v) { this.centroMasConectado = v; }
        public int getGradoMaximo() { return gradoMaximo; }
        public void setGradoMaximo(int v) { this.gradoMaximo = v; }
    }

    public static class ErrorDTO {
        private int status;
        private String mensaje;
        private String detalle;
        public ErrorDTO() {}
        public ErrorDTO(int status, String mensaje, String detalle) {
            this.status = status; this.mensaje = mensaje; this.detalle = detalle;
        }
        public int getStatus() { return status; }
        public void setStatus(int v) { this.status = v; }
        public String getMensaje() { return mensaje; }
        public void setMensaje(String v) { this.mensaje = v; }
        public String getDetalle() { return detalle; }
        public void setDetalle(String v) { this.detalle = v; }
    }
}
