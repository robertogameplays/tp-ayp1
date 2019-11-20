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
    public void checkObtenerCasillero(){
    	this.juego.soltarFicha(2);
    	Assert.assertEquals(Casillero.ROJO, this.juego.obtenerCasillero(this.fila, 2));
    }
   
    @Test
    public void checkNoExisteNombreGanador() {
        Assert.assertSame("", juego.obtenerGanador());
    }
    @Test (expected =Error.class)
    public void checkErrorSoltarFicha(){
    this.juego.soltarFicha(-2);
    }
    @Test (expected = Error.class)
    public void checkErrorObtenerCasillero(){
    	this.juego.obtenerCasillero(-2, -1321);
    } 
    @Test
    public void checkGanadorHorizontal(){
    	for(int i=1;i<4;i++){
    		this.juego.soltarFicha(i);
    		this.juego.soltarFicha(i);
    	}
    	this.juego.soltarFicha(4);
    	Assert.assertTrue(this.juego.hayGanador());
    	
    }
    @Test
    public void checkGanadorVertical(){
    	for(int i=1;i<4;i++){
    		this.juego.soltarFicha(1);
    		this.juego.soltarFicha(2);
    	}
    	this.juego.soltarFicha(1);
    	Assert.assertTrue(this.juego.hayGanador());
    	
    }
}
