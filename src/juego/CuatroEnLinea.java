package juego;

import javafx.scene.control.Alert;

/**
 * Juego Cuatro en Lï¿½nea
 * 
 * Reglas:
 * 
 * 		...
 *
 */
public class CuatroEnLinea {

    private int filas=0, columnas=0;
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
	 * 		 Todo el tablero estï¿½ vacï¿½o.
	 * 
	 * @param filas : cantidad de filas que tiene el tablero.
	 * @param columnas : cantidad de columnas que tiene el tablero.
	 * @param jugadorRojo : nombre del jugador con fichas rojas.
	 * @param jugadorAmarillo : nombre del jugador con fichas amarillas.
	 */
	public CuatroEnLinea(int filas, int columnas, String jugadorRojo, String jugadorAmarillo) {
		if(jugadorRojo.equalsIgnoreCase(jugadorAmarillo)) {
			this.alertTablero("Los nombres deben ser distintos");
		}else{
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
			this.alertTablero("Los numeros deben ser mayor o igual a 4, y menor a 10");
        }
		}
	}

	/**
	 * post: devuelve la cantidad mï¿½xima de fichas que se pueden apilar en el tablero.
	 */
	public int contarFilas() {
		return this.filas;
	}

	/**
	 * post: devuelve la cantidad mï¿½xima de fichas que se pueden alinear en el tablero.
	 */
	public int contarColumnas() {
		return this.columnas;
	}

	/**
	 * pre : fila estï¿½ en el intervalo [1, contarFilas()],
	 * 		 columnas estï¿½ en el intervalo [1, contarColumnas()].
	 * post: indica quï¿½ ocupa el casillero en la posiciï¿½n dada por fila y columna.
	 * 
	 * @param fila
	 * @param columna
	 */
	public Casillero obtenerCasillero(int fila, int columna) {
		if(fila <= this.filas && fila > 0 && columna > 0 && columna <= this.contarColumnas()){
			return casilleros[fila-1][columna-1];
		}else{
			throw new Error("fila y columna dentro del maximo y minimo");
		}
	}
	
	/**
	 * pre : el juego no terminï¿½, columna estï¿½ en el intervalo [1, contarColumnas()]
	 * 		 y aï¿½n queda un Casillero.VACIO en la columna indicada. 
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
			throw new Error ("Columna llena");
		}
	}
	
	/**
	 * post: indica si el juego terminï¿½ porque uno de los jugadores
	 * 		 ganï¿½ o no existen casilleros vacï¿½os.
	 */
	public boolean termino() {
		return (this.hayGanador || this.contadorVacios == 0);
	}

	/**
	 * post: indica si el juego terminï¿½ y tiene un ganador.
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
	 * pre : el juego terminï¿½.
	 * post: devuelve el nombre del jugador que ganï¿½ el juego.
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
		int countColor = this.objCasilleros.contarColoresHorizontal(this.casilleros, filaActual, columnaActual, this.columnas - 1, this.colorActivo, true);
		if(countColor >= totalFichasParaGanar) {
			this.hayGanador = true;
		}else {
			countColor += this.objCasilleros.contarColoresHorizontal(this.casilleros, filaActual, columnaActual-1, 0, this.colorActivo, false);
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
		int countColor = this.objCasilleros.contarColoresVertical(this.casilleros, filaActual, columnaActual, this.filas-1, this.colorActivo, true);
		if(countColor >= totalFichasParaGanar) {
			this.hayGanador = true;
		}else {
			countColor += this.objCasilleros.contarColoresVertical(this.casilleros, filaActual-1, columnaActual, 0, this.colorActivo, false);
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
		int countColor = this.objCasilleros.contarColoresDiagonalNegativa(this.casilleros, filaActual, columnaActual, this.filas, this.columnas, this.colorActivo);
		if(countColor >= totalFichasParaGanar) {
			this.hayGanador =  true;
		}else {
			countColor = this.objCasilleros.contarColoresDiagonalPositiva(this.casilleros, filaActual, columnaActual, this.filas, this.columnas, this.colorActivo);
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
