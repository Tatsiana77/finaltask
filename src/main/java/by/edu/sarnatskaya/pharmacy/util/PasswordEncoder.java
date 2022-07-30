package by.edu.sarnatskaya.pharmacy.util;

import java.math.BigInteger;
import java.util.Base64;

/**
 * Class PasswordEncoder.
 *
 * Encode password
 * @author  Tatiana Sarnatskaya
 *
 */

public class PasswordEncoder {
    private PasswordEncoder() {
    }
    /**
     * @param password
     * @return String contains value data of password
     */

    public static String passwordEncoding(String password) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytesEncoded = encoder.encode(password.getBytes());
        BigInteger bigInteger = new BigInteger(1, bytesEncoded);
        String resultData = bigInteger.toString();
        return resultData;
    }
}
