package juego;

import javafx.scene.control.Alert;

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
    private String jugadorRojo, jugadorAmarillo, jugadorActivo, jugarGanador;
    private Casillero[][] casilleros;
    private Casillero colorActivo;
    private boolean hayGanador;
    private int contadorVacios;
    private Casilleros objCasilleros;
    private boolean casillaDisponible;

    static final int minNumeroPermitido = 4;
	static final int maxNumeroPermitido = 10;
	static final int totalFichasParaGanar = 4;

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
            this.contadorVacios = filas * columnas;
            this.jugarGanador = "";
            this.objCasilleros = new Casilleros();
            this.casillaDisponible = true;
        }else {
			this.alertTablero("Los numeros deben ser mayor o igual a 4");
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
		if(!this.termino() && 1 <= columna && columna <= this.contarColumnas()) {
			for(int i = this.filas - 1; i >= 0; i--) {
				if (this.casilleros[i][columna - 1] == Casillero.VACIO) {
					this.colorActivo = this.checkJugadorActivo();
					this.casilleros[i][columna - 1] = this.colorActivo;

					if(!this.hayGanador()) this.ganadorLineaHorizontal(i, columna - 1);
					if(!this.hayGanador()) this.ganadorLineaVertical(i, columna - 1);
					if(!this.hayGanador()) this.ganadorLineaDiagonal(i, columna - 1);

					if(!this.hayGanador()) {
						this.contadorVacios--;
						this.cambiarTurno();
					}
					break;
				}
			}
		}else {
			System.exit(0);
		}
	}
	
	/**
	 * post: indica si el juego termin� porque uno de los jugadores
	 * 		 gan� o no existen casilleros vac�os.
	 */
	public boolean termino() {
		return (this.hayGanador || this.contadorVacios == 0);
	}

	/**
	 * post: indica si el juego termin� y tiene un ganador.
	 */
	public boolean hayGanador() {
		if(this.hayGanador) {
			this.jugarGanador = this.jugadorActivo;
			return true;
		}else {
			return false;
		}
	}

	/**
	 * pre : el juego termin�.
	 * post: devuelve el nombre del jugador que gan� el juego.
	 */
	public String obtenerGanador() {
		return this.jugarGanador;
	}

    /**
     * post: devuelve verdadero o falso segun el valor
     * @param numero
     * @return
     */
	private boolean validarNumeros(int numero) {
	    if((numero >= minNumeroPermitido) && (numero<=maxNumeroPermitido)){
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
	private void ganadorLineaHorizontal(int filaActual, int columnaActual) {
		int countColor = this.objCasilleros.contarColoresHorizontal(this.casilleros, filaActual, columnaActual, this.columnas, this.colorActivo);
		if(countColor >= totalFichasParaGanar) {
			this.hayGanador = true;
		}else {
			countColor += this.objCasilleros.contarColoresHorizontal(this.casilleros, filaActual, 0, columnaActual, this.colorActivo);
			if (countColor >= totalFichasParaGanar) {
				this.hayGanador = true;
			} else {
				this.hayGanador = false;
			}
		}

	}

	/**
	 * post: funcion que retorna si gano un jugador por columna
	 * @param filaActual
	 * @param columnaActual
	 */
	private void ganadorLineaVertical(int filaActual, int columnaActual) {
		int countColor = this.objCasilleros.contarColoresVertical(this.casilleros, filaActual, columnaActual, this.filas, this.colorActivo);
		if(countColor >= totalFichasParaGanar) {
			this.hayGanador = true;
		}else {
			countColor += this.objCasilleros.contarColoresVertical(this.casilleros, 0, columnaActual, filaActual, this.colorActivo);
			if(countColor >= totalFichasParaGanar) {
				this.hayGanador =  true;
			}else {
				this.hayGanador =  false;
			}
		}
	}

	/**
	 * post funcion que retorna si gano un jugador por diagonal
	 * @param filaActual
	 * @param columnaActual
	 */
	private void ganadorLineaDiagonal(int filaActual, int columnaActual) {
		int countColor = this.objCasilleros.contarColoresDiagonal(this.casilleros, filaActual, columnaActual, this.filas, this.columnas, this.colorActivo);
		if(countColor >= totalFichasParaGanar) {
			this.hayGanador =  true;
		}else {
			countColor += this.objCasilleros.contarColoresDiagonal(this.casilleros, 0, 0, filaActual, columnaActual, this.colorActivo);
			if(countColor >= totalFichasParaGanar) {
				this.hayGanador =  true;
			}else {
				this.hayGanador =  false;
			}
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

	/**
	 * post: funcion que muestra un mensaje
	 * @param msg
	 */
	private void alertTablero(String msg) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Alerta del tablero");
		alert.setHeaderText(null);
		alert.setContentText(msg);

		alert.showAndWait();
	}
}
