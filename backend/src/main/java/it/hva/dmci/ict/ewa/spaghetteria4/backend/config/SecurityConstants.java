package it.hva.dmci.ict.ewa.spaghetteria4.backend.config;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Constants used for JWT
 *
 * @author Sam Toxopeus
 */
public class SecurityConstants {
    public static final String SECRET = generateSafeToken();
    public static final long EXPIRATION_TIME = 43_200_000; // 12 hours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/signup";

    private static String generateSafeToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}
