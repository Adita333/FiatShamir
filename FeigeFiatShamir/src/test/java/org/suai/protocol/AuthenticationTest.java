package org.suai.protocol;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.util.Random;
import java.util.logging.Logger;

public class AuthenticationTest {

    private static final Logger logger = Logger.getLogger(AuthenticationTest.class.getName());
    private TrustCenter trustCenter;
    private Authentication authentication;
    private Random random;

    @BeforeEach
    public void setUp() {
        trustCenter = new TrustCenter();
        authentication = new Authentication();
        random = new Random();
    }

    @Test
    public void testAuthenticationSuccess() {
        int t = 10;
        BigInteger n = trustCenter.generateN();
        int k = 5;

        boolean result = authentication.authentication(t, n, k);
        assertTrue(result, "La autenticación debería ser exitosa para t = " + t);
        logger.info("La autenticación fue exitosa para t = " + t + " y k = " + k + ", como se esperaba.");
    }

    @Test
    public void testFalseAuthenticationFailure() {
        BigInteger n = trustCenter.generateN();
        int k = 6;

        boolean result = authentication.falseAuthentication(n, k);
        assertFalse(result, "La autenticación falsa NO debería ser exitosa.");
        logger.info("La autenticación falsa fue no exitosa, como se esperaba.");
    }

    @Test
    public void testAuthenticationWithZeroAttempts() {
        int t = 0; // No debería autenticar
        BigInteger n = trustCenter.generateN();
        int k = 3;

        boolean result = authentication.authentication(t, n, k);
        assertFalse(result, "La autenticación debería fallar si no se hacen intentos.");
        logger.info("La autenticación falló como se esperaba al no hacer intentos.");
    }

    @Test
    public void testAuthenticationWithNegativeAttempts() {
        int t = -1; // No debería autenticar
        BigInteger n = BigInteger.valueOf(12345);
        int k = 3;

        boolean result = authentication.authentication(t, n, k);
        assertFalse(result, "La autenticación debería fallar con intentos negativos.");
        logger.info("La autenticación falló como se esperaba con intentos negativos.");
    }

    @Test
    public void testRandomValuesAuthentication() {
        int t = random.nextInt(10) + 1; // t entre 1 y 10
        int k = random.nextInt(5) + 1;   // k entre 1 y 5
        BigInteger n = trustCenter.generateN();

        logger.info("Probando con t: " + t + ", k: " + k + ", n: " + n);

        boolean result = authentication.authentication(t, n, k);
        assertTrue(result, "La autenticación debería ser exitosa para t = " + t);
        logger.info("La autenticación fue exitosa, como se esperaba.");
    }

    @Test
    public void testRandomValuesFalseAuthentication() {
        int k = random.nextInt(5) + 1;   // k entre 1 y 5
        BigInteger n = trustCenter.generateN();

        logger.info("Probando autenticación falsa con k: " + k + ", n: " + n);

        boolean result = authentication.falseAuthentication(n, k);
        assertFalse(result, "La autenticación falsa NO debería ser exitosa.");
        logger.info("La autenticación falsa fue no exitosa, como se esperaba.");
    }
}