/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.prog3.tarea4.model;

/**
 *
 * @author José Sacrab
 */
public class Aristas implements Comparable<Aristas> {

    private String origen;
    private String destino;
    private int peso;

    public Aristas() {}

    public Aristas(String origen, String destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public int getPeso() { return peso; }
    public void setPeso(int peso) { this.peso = peso; }

    @Override
    public int compareTo(Aristas otra) {
        return Integer.compare(this.peso, otra.peso);
    }
}
 