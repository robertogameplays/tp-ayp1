package juego;

/**
 * Juego Cuatro en L�nea
 * 
 * Reglas:
 * 
 * 		...
 *
 */
public class CuatroEnLinea {

    private int filas, columnas;
    private String jugadorRojo, jugadorAmarillo, jugadorActivo;
    private Casillero[][] casilleros;
    private Casillero colorActivo;
    private boolean hayGanador;

	/**
	 * pre : 'filas' y 'columnas' son mayores o iguales a 4.
	 * post: empieza el juego entre el jugador que tiene fichas rojas, identificado como 
	 * 		 'jugadorRojo' y el jugador que tiene fichas amarillas, identificado como
	 * 		 'jugadorAmarillo'. 
	 * 		 Todo el tablero est� vac�o.
	 * 
	 * @param filas : cantidad de filas que tiene el tablero.
	 * @param columnas : cantidad de columnas que tiene el tablero.
	 * @param jugadorRojo : nombre del jugador con fichas rojas.
	 * @param jugadorAmarillo : nombre del jugador con fichas amarillas.
	 */
	public CuatroEnLinea(int filas, int columnas, String jugadorRojo, String jugadorAmarillo) {
        if(this.validarNumeros(filas) && this.validarNumeros(columnas)) {
			this.crearTablero(filas, columnas);
            this.filas = filas;
            this.columnas = columnas;
            this.jugadorRojo = jugadorRojo;
            this.jugadorAmarillo = jugadorAmarillo;
            this.jugadorActivo = this.jugadorRojo;
            this.colorActivo = Casillero.ROJO;
            this.hayGanador = false;
        }else {
            Error err = new Error("Los numeros deben ser mayor o igual a 4");
            throw err;
        }
	}

	/**
	 * post: devuelve la cantidad m�xima de fichas que se pueden apilar en el tablero.
	 */
	public int contarFilas() {
		return this.filas;
	}

	/**
	 * post: devuelve la cantidad m�xima de fichas que se pueden alinear en el tablero.
	 */
	public int contarColumnas() {
		return this.columnas;
	}

	/**
	 * pre : fila est� en el intervalo [1, contarFilas()],
	 * 		 columnas est� en el intervalo [1, contarColumnas()].
	 * post: indica qu� ocupa el casillero en la posici�n dada por fila y columna.
	 * 
	 * @param fila
	 * @param columna
	 */
	public Casillero obtenerCasillero(int fila, int columna) {
		return casilleros[fila-1][columna-1];
	}
	
	/**
	 * pre : el juego no termin�, columna est� en el intervalo [1, contarColumnas()]
	 * 		 y a�n queda un Casillero.VACIO en la columna indicada. 
	 * post: deja caer una ficha en la columna indicada.
	 * 
	 * @param columna
	 */
	public void soltarFicha(int columna) {
		if(1 <= columna && columna <= this.contarColumnas()) {
			int i = this.filas - 1;
			while (i >= 0) {
				if (this.casilleros[i][columna - 1] == Casillero.VACIO) {
					this.colorActivo = this.checkJugadorActivo();
					this.casilleros[i][columna - 1] = this.colorActivo;
					this.ganadorLineaHorizontal(i, columna - 1);
					break;
				}
				i--;
			}
			this.cambiarTurno();
		}else {
			throw new Error("El parametro culumna debe encontrarse en el rango [1, " + this.contarColumnas() +"]");
		}
	}
	
	/**
	 * post: indica si el juego termin� porque uno de los jugadores
	 * 		 gan� o no existen casilleros vac�os.
	 */
	public boolean termino() {
		return this.hayGanador;
	}

	/**
	 * post: indica si el juego termin� y tiene un ganador.
	 */
	public boolean hayGanador() {
		return this.hayGanador;
	}

	/**
	 * pre : el juego termin�.
	 * post: devuelve el nombre del jugador que gan� el juego.
	 */
	public String obtenerGanador() {
		return this.jugadorActivo;
	}

    /**
     * post: devuelve verdadero o falso segun el valor
     * @param numero
     * @return
     */
	private boolean validarNumeros(int numero) {
	    if((numero >= 4) && (numero<=10)){
	        return true;
        }else {
	        return false;
        }
    }

	/**
	 * post: funcion que crear array vacio
	 */
	private void crearTablero(int filas, int columnas) {
		this.casilleros = new Casillero[filas][columnas];

		for(int i = 0; i < filas; i++) {
			for(int j = 0; j < columnas; j++) {
				this.casilleros[i][j] = Casillero.VACIO;
			}
		}
	}

	/**
	 * post: funcion que retorna el jugador activo y cambia el jugador
	 * @return
	 */
	private Casillero checkJugadorActivo() {
		if(this.jugadorActivo.equals(this.jugadorRojo)) {
			return Casillero.ROJO;
		}else {
			return Casillero.AMARILLO;
		}
	}

	/**
	 * post: funcion que retorna si gano un jugador por fila
	 * @param filaActual
	 * @param columnaActual
	 */
	public void ganadorLineaHorizontal(int filaActual, int columnaActual) {
		int countColor = 0;

		for(int i = columnaActual; i < this.columnas; i++) {
			if(this.casilleros[filaActual][i].equals(this.colorActivo)) {
				countColor++;
			}
		}
		if(countColor >= 4) {
			this.hayGanador = true;
		}else {
			for(int i = 0; i < columnaActual; i++) {
				if(this.casilleros[filaActual][i].equals(this.colorActivo)) {
					countColor++;
				}
			}
		}
		if(countColor >= 4) {
			this.hayGanador =  true;
		}else {
			this.hayGanador =  false;
		}
	}

	/**
	 * post: funcion que cambia el jugaador activo
	 */
	private void cambiarTurno() {
		if(this.jugadorActivo.equals(this.jugadorRojo)) {
			this.jugadorActivo = this.jugadorAmarillo;
		}else {
			this.jugadorActivo = this.jugadorRojo;
		}
	}
}
