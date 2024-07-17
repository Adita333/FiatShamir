package org.suai.protocol;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La parte A demuestra su conocimiento del secreto s a la parte B durante t rondas,
 * sin revelar ningún bit del secreto en sí mismo.
 */
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());
    private static final TrustCenter trustCenter = new TrustCenter();
    private static final Authentication identification = new Authentication();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        logger.info("Ingrese el numero de rondas: ");
        int t = scanner.nextInt();

        logger.info("Ingrese el numero k: ");
        int k = scanner.nextInt();

        authentication(t, k);
        falseAuthentication(k);
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
