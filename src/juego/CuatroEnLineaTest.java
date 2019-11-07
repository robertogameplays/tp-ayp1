package juego;

import org.junit.*;

public class CuatroEnLineaTest {
    private CuatroEnLinea juego;
    private int fila = 4, columna = 4;
    @Before
    public void init() {
        this.juego = new CuatroEnLinea(fila, columna, "Jugador1", "Jugador2");
    }

    @Test
    public void checkMinimoNumeroPermitido() {
        Assert.assertTrue(this.fila >= CuatroEnLinea.minNumeroPermitido && this.columna >= CuatroEnLinea.minNumeroPermitido);
    }

    @Test
    public void checkMaximoNumeroPermitido() {
        Assert.assertTrue(this.fila <= CuatroEnLinea.maxNumeroPermitido && this.columna <= CuatroEnLinea.maxNumeroPermitido);
    }

    @Test
    public void checkContarFilas() {
        Assert.assertEquals(fila, this.juego.contarFilas());
    }

    @Test
    public void checkContarColumnas() {
        Assert.assertEquals(columna, this.juego.contarColumnas());
    }

    @Test
    public void checkFailTerminoJuego() {
        Assert.assertFalse(juego.termino());
    }

    @Test
    public void checkNoExisteGanador() {
        Assert.assertFalse(juego.hayGanador());
    }

    @Test
    public void checkNoExisteNombreGanador() {
        Assert.assertSame("", juego.obtenerGanador());
    }
}
