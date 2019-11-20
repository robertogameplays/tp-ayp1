package juego;

public class Casilleros {
    /**
     * post: Funcion que devuelve la cantidad de coleres que hay en diagonal
     * @param casilleros
     * @param filaInicial
     * @param columnaInicial
     * @param filaFinal
     * @param columnaFinal
     * @param colorActivo
     * @return
     */
    public int contarColoresDiagonalNegativa(Casillero[][] casilleros, int filaInicial, int columnaInicial, int filaFinal, int columnaFinal, Casillero colorActivo) {
        int filaIdice = filaInicial, columnaIdice = columnaInicial, countColor = 0;
        while (filaIdice < filaFinal && columnaIdice < columnaFinal && casilleros[filaIdice][columnaIdice].equals(colorActivo)) {
            countColor++;
            filaIdice++;
            columnaIdice++;
        }
        return countColor;
    }
    /**
     * post: Funcion que devuelve la cantidad de coleres que hay en diagonal
     * @param casilleros
     * @param filaInicial
     * @param columnaInicial
     * @param filaFinal
     * @param columnaFinal
     * @param colorActivo
     * @return
     */
    public int contarColoresDiagonalPositiva(Casillero[][] casilleros, int filaInicial, int columnaInicial, int filaFinal, int columnaFinal, Casillero colorActivo) {
        int filaIdice = filaInicial, columnaIdice = columnaInicial, countColor = 0;
        while (filaIdice < filaFinal && columnaIdice > columnaFinal && casilleros[filaIdice][columnaIdice].equals(colorActivo)) {
            countColor++;
            filaIdice++;
            columnaIdice--;
        }
        return countColor;
    }

    /**
     * post: Funcion que devuelve la cantidad de coleres que hay en horizontal
     * @param casilleros
     * @param filaActual
     * @param columnaActual
     * @param columnaFinal
     * @param colorActivo
     * @return
     */
    public int contarColoresHorizontal(Casillero[][] casilleros, int filaActual, int columnaActual, int columnaFinal, Casillero colorActivo, boolean incrementa) {
        int countColor = 0;
        if(incrementa) {
            for (int i = columnaActual; i < columnaFinal; i++) {
                if (casilleros[filaActual][i].equals(colorActivo)) {
                    countColor++;
                } else {
                    break;
                }
            }
        }else {
            for (int i = columnaActual; i >= columnaFinal; i--) {
                if (casilleros[filaActual][i].equals(colorActivo)) {
                    countColor++;
                } else {
                    break;
                }
            }
        }
        return countColor;
    }

    /**
     * post: Funcion que devuelve la cantidad de coleres que hay en vertical
     * @param casilleros
     * @param filaActual
     * @param columnaActual
     * @param filaFinal
     * @param colorActivo
     * @return
     */
    public int contarColoresVertical(Casillero[][] casilleros, int filaActual, int columnaActual, int filaFinal, Casillero colorActivo, boolean incrementa) {
        int countColor = 0;
        if(incrementa) {
            for (int i = filaActual; i <= filaFinal; i++) {
                if (casilleros[i][columnaActual].equals(colorActivo)) {
                    countColor++;
                } else {
                    break;
                }
            }
        }else {
            for (int i = filaActual; i > filaFinal; i--) {
                if (casilleros[i][columnaActual].equals(colorActivo)) {
                    countColor++;
                } else {
                    break;
                }
            }
        }
        return countColor;
    }
}
