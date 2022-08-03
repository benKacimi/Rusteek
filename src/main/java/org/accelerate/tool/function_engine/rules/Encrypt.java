package org.accelerate.tdm.smartdata.rules;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("encrypt")
public class Encrypt implements IRule{

    protected static final Logger LOGGER = LoggerFactory.getLogger(Encrypt.class);

    private static final SecretKeySpec KEY = initKey();

    static SecretKeySpec initKey(){
        try {
            SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }


    @Function (name="AESEncrypt")
    public  String encrypt(String something) {
        Objects.requireNonNull(something);
        try {
            Cipher cipher = Cipher.getInstance("RSA/None/OAEPWITHSHA-256ANDMGF1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, KEY);
            final byte[] encryptedBytes = cipher.doFinal(something.getBytes(StandardCharsets.UTF_8));
            return new String(encryptedBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            // do nothing
            return something;
        }
    }
}
