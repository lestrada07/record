package com.nisum.api.record.utility;

import io.jsonwebtoken.Jwts;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Utility {


    public static boolean validateMail(String email){
        // Pattern to validate the email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        return mather.find();
    }

    public static boolean isNumeric(String cadena) {
        try {
            Long.parseLong(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static String generateUUID(){
        String uuidString = "";
        UUID uuid = UUID.randomUUID();
        uuidString = uuid.toString();
        return uuidString;
    }

    public static boolean validatePassword(String password) throws IOException {
        String regularExp = "";
        InputStream input = Utility.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();

        prop.load(input);

        regularExp = prop.getProperty("passwRegExp");

        // Pattern to validate password
        Pattern pattern = Pattern.compile(regularExp);

        Matcher mather = pattern.matcher(password);
        return mather.find();
    }

    public static String generateToken(String name, String email, String uuid) throws IOException {
        InputStream input = Utility.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();
        prop.load(input);
        Long duration = 0l;
        duration = Long.parseLong(prop.getProperty("token.duration"));
        String jwtToken = Jwts.builder()
                .claim("name", name)
                .claim("email", email)
                .setSubject(uuid)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(duration, ChronoUnit.MINUTES)))
                .compact();
        return jwtToken;
    }

    public static String encrypt(String text) throws IOException {
        String secretKey = "";
        String base64EncryptedString = "";
        InputStream input = Utility.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();

        prop.load(input);

        secretKey = prop.getProperty("secretKey");

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = text.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public static String decrypt(String encryptText) throws Exception {
        String secretKey = "";
        String base64EncryptedString = "";
        InputStream input = Utility.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();

        prop.load(input);

        secretKey = prop.getProperty("secretKey");

        try {
            byte[] message = Base64.decodeBase64(encryptText.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }
}
