package org.suai.protocol;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.logging.Logger;

import static java.lang.System.out;

public class Authentication {
    private static final Logger logger = Logger.getLogger(Authentication.class.getName());
    private int tempCount = 0;

    /**
     * El participante se identifica ante el entorno utilizando los valores (v_1, v_2, ..., v_k; n),
     * que actúan como su clave pública, mientras que la clave secreta s = (s_1, s_2, ..., s_k)
     * solo es conocida por el participante.
     */
    public boolean authentication(int t, BigInteger n, int k) {
        // Validaciones para el número de intentos
        if (t <= 0) {
            logger.warning("El número de intentos debe ser mayor que cero.");
            return false; // No se puede autenticar si t es 0 o negativo
        }

        Alice alice = new Alice(n, k);
        Bob bob = new Bob(n, k);

        for (int i = 0; i < t; i++) {
            alice.generator(); // Alice genera parámetros
            BigInteger r = alice.generateR(); // Alice elige r
            out.println("r: " + r);
            int b = alice.generateB(); // Alice elige b
            BigInteger x = alice.calculateX(b, r); // Alice calcula X y lo envía a Bob
            int[] e = bob.generateVectorE(); // Bob genera el vector e y lo envía a Alice
            BigInteger y = alice.generateY(e, r); // r * producto por s
            boolean flag = bob.generateZ(y, x); // y^2 producto
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    public boolean falseAuthentication(BigInteger n, int k) {
        Alice alice = new Alice(n, k);
        alice.generator();
        Bob bob = new Bob(n, k);
        Eva eva = new Eva(n, k);
    
        int[] e = bob.generateVectorE();
        out.println("Se utilizó el vector e: " + Arrays.toString(e) + " - intentemos adivinarlo.");
    
        int count = 0;
        
        while (tempCount < 10) { // Límite de intentos
            BigInteger[] x = eva.getX();
            BigInteger y = eva.getR(); 
    
            if (bob.checkZ(y, x)) {
                return true; // Autenticación falsa exitosa
            }
    
            count++;
    
            if (count >= Math.pow(2, k) - 1) {
                tempCount++;
                logger.info(() -> "Fallo en el intento numero: " + tempCount);
    
                count = 0; // Reiniciar el contador de intentos
            }
        }
        logger.info("Se ha superado el limite de intentos permitidos.");
        return false; // Termina si se superan los intentos permitidos
    } 
}