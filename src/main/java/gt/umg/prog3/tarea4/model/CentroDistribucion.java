package gt.umg.prog3.tarea4.model;

/**
 * Modelo que representa un centro de distribución logística.
 * Tiene un nombre descriptivo, código único y región geográfica.
 *
 * @author JoseSacrab4719
 * @version 1.0
 */
public class CentroDistribucion {

    private String nombre;
    private String codigo;
    private String region;

    /**
     * Constructor por defecto para serialización JSON.
     */
    public CentroDistribucion() {}

    /**
     * Constructor con todos los atributos.
     *
     * @param nombre el nombre del centro
     * @param codigo el código único identificador
     * @param region la región geográfica
     */
    public CentroDistribucion(String nombre, String codigo, String region) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.region = region;
    }

    /** @return el nombre del centro */
    public String getNombre() { return nombre; }
    /** @param nombre el nuevo nombre */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return el código del centro */
    public String getCodigo() { return codigo; }
    /** @param codigo el nuevo código */
    public void setCodigo(String codigo) { this.codigo = codigo; }

    /** @return la región del centro */
    public String getRegion() { return region; }
    /** @param region la nueva región */
    public void setRegion(String region) { this.region = region; }
}