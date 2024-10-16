package org.suai.protocol;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Eva {
    private BigInteger n;
    private int k;
    private List<BigInteger> xList; // Lista para almacenar los valores de x
    private BigInteger r; // Valor r que Eva elige
    private Random random; // Generador aleatorio

    public Eva(BigInteger n, int k) {
        this.n = n;
        this.k = k;
        this.xList = new ArrayList<>(k); // Inicializa la lista con capacidad k
        this.random = new Random(); // Inicializa el generador aleatorio
        generateX(); // Genera los valores de x inicialmente
        this.r = generateR(); // Genera r
    }

    private void generateX() {
        for (int i = 0; i < k; i++) {
            // Genera un valor aleatorio dentro del rango de n
            BigInteger xValue = new BigInteger(n.bitLength(), random).mod(n);
            xList.add(xValue);
        }
    }

    private BigInteger generateR() {
        // Genera un valor r aleatorio dentro del rango de n
        return new BigInteger(n.bitLength(), random).mod(n);
    }

    public BigInteger[] getX() {
        // Verifica que la lista xList no esté vacía
        if (xList.isEmpty()) {
            throw new IllegalStateException("La lista x no está inicializada o está vacía");
        }
        return xList.toArray(new BigInteger[0]); // Devuelve una copia de la lista de x
    }

    public BigInteger getR() {
        return r; // Retorna el valor r
    }
}