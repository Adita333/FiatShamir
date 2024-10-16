package org.suai.protocol;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La parte A demuestra su conocimiento del secreto s a la parte B durante t
 * rondas,
 * sin revelar ningun bit del secreto en si mismo.
 */
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());
    private static final TrustCenter trustCenter = new TrustCenter();
    private static final Authentication identification = new Authentication();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            logger.info("Ingrese el numero de rondas: ");
            int t = scanner.nextInt();
    
            logger.info("Ingrese el numero k: ");
            int k = scanner.nextInt();

            // Validacion de t y k antes de la autenticacion
            if (t <= 0) {
                logger.warning("El numero de rondas debe ser mayor que cero. Autenticacion no realizada.");
                return; // Termina la ejecucion si t no es valido
            }

            if (k <= 0) {
                logger.warning("El valor de k debe ser mayor que cero. Autenticacion no realizada.");
                return; // Termina la ejecucion si k no es valido
            }
    
            authentication(t, k);
            falseAuthentication(k);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error en la entrada: ", e);
        }
    }

    private static void authentication(int t, int k) {
        BigInteger n = trustCenter.generateN();
        logger.log(Level.INFO, () -> "n: " + n);

        if (identification.authentication(t, n, k)) {
            logger.info("Autenticacion exitosa");
        } else {
            logger.info("Autenticacion no exitosa");
        }
    }

    private static void falseAuthentication(int k) {
        BigInteger n = trustCenter.generateN();
        logger.log(Level.INFO, () -> "n: " + n);

        if (identification.falseAuthentication(n, k)) {
            logger.info("Autenticacion falsa exitosa");
        } else {
            logger.info("Autenticacion falsa no exitosa");
        }
    }
}