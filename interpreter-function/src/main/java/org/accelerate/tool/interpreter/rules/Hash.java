package org.accelerate.tool.interpreter.rules;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("hash")
public class Hash implements Rule{

    protected static final Logger LOGGER = LoggerFactory.getLogger(Hash.class);

    @Function (name="SHA3256")
    public  String hash(String something) {
        if("".equals(something) || something == null)
            return "";

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA3-256");
            final byte[] hashbytes = digest.digest(
                something.getBytes(StandardCharsets.UTF_8));
                return bytesToHex(hashbytes);

        } catch (NoSuchAlgorithmException e) {
          return "error SHA3-256";
        } 
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
