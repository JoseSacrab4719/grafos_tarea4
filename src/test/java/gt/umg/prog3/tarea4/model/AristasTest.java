/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gt.umg.prog3.tarea4.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author José Sacrab
 */
public class AristasTest {
    
    public AristasTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getOrigen method, of class Arista.
     */
    @Test
    public void testGetOrigen() {
        System.out.println("getOrigen");
        Aristas instance = new Aristas();
        String expResult = "";
        String result = instance.getOrigen();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOrigen method, of class Arista.
     */
    @Test
    public void testSetOrigen() {
        System.out.println("setOrigen");
        String origen = "";
        Aristas instance = new Aristas();
        instance.setOrigen(origen);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDestino method, of class Arista.
     */
    @Test
    public void testGetDestino() {
        System.out.println("getDestino");
        Aristas instance = new Aristas();
        String expResult = "";
        String result = instance.getDestino();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDestino method, of class Arista.
     */
    @Test
    public void testSetDestino() {
        System.out.println("setDestino");
        String destino = "";
        Aristas instance = new Aristas();
        instance.setDestino(destino);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPeso method, of class Arista.
     */
    @Test
    public void testGetPeso() {
        System.out.println("getPeso");
        Aristas instance = new Aristas();
        int expResult = 0;
        int result = instance.getPeso();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPeso method, of class Arista.
     */
    @Test
    public void testSetPeso() {
        System.out.println("setPeso");
        int peso = 0;
        Aristas instance = new Aristas();
        instance.setPeso(peso);
        // TODO review the generated test code and remove the default call to fail.
        fail("prueba es proto.");
    }

    /**
     * Test of compareTo method, of class Arista.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Aristas otra = null;
        Aristas instance = new Aristas();
        int expResult = 0;
        int result = instance.compareTo(otra);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
