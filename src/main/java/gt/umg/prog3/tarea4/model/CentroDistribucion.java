package gt.umg.prog3.tarea4.model;

public class CentroDistribucion {
    private String nombre;
    private String codigo;
    private String region;

    public CentroDistribucion() {}
    public CentroDistribucion(String nombre, String codigo, String region) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.region = region;
    }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
}
