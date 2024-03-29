package com.jvfast.common.util;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.BCrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class EncryptUtil {
    /**
     * 密码加密
     *
     * @param content
     * @return
     */
    public static String encryptPasswd(String content) {
        return BCrypt.hashpw(content);
    }

    /**
     * 密码校验
     *
     * @param plaintext
     * @param hashed
     * @return
     */
    public static boolean checkPasswd(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }


    /**
     * Encrypt and decrypt AES-256 (in CryptoJS way)
     * Encrypt
     *
     * @param plaintext  plain string
     * @param passphrase passphrase
     * @return
     */
    public static String AESEncode(String plaintext, String passphrase) {
        try {
            final int keySize = 256;
            final int ivSize = 128;

            // Create empty key and iv
            byte[] key = new byte[keySize / 8];
            byte[] iv = new byte[ivSize / 8];

            // Create random salt
            byte[] saltBytes = generateSalt(8);

            // Derive key and iv from passphrase and salt
            EvpKDF(passphrase.getBytes(StandardCharsets.UTF_8), keySize, ivSize, saltBytes, key, iv);

            // Actual encrypt
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            byte[] cipherBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            /**
             * Create CryptoJS-like encrypted string from encrypted data
             * This is how CryptoJS do:
             * 1. Create new byte array to hold ecrypted string (b)
             * 2. Concatenate 8 bytes to b
             * 3. Concatenate salt to b
             * 4. Concatenate encrypted data to b
             * 5. Encode b using Base64
             */
            byte[] sBytes = "Salted__".getBytes(StandardCharsets.UTF_8);
            byte[] b = new byte[sBytes.length + saltBytes.length + cipherBytes.length];
            System.arraycopy(sBytes, 0, b, 0, sBytes.length);
            System.arraycopy(saltBytes, 0, b, sBytes.length, saltBytes.length);
            System.arraycopy(cipherBytes, 0, b, sBytes.length + saltBytes.length, cipherBytes.length);

            byte[] base64b = Base64.getEncoder().encode(b);

            return new String(base64b);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Encrypt and decrypt AES-256 (in CryptoJS way)
     * Decrypt
     * Thanks Artjom B. for this: http://stackoverflow.com/a/29152379/4405051
     *
     * @param ciphertext encrypted string
     * @param passphrase passphrase
     */
    public static String AESDecode(String ciphertext, String passphrase) {
        try {
            final int keySize = 256;
            final int ivSize = 128;

            // Decode from base64 text
            byte[] ctBytes = Base64.getDecoder().decode(ciphertext.getBytes(StandardCharsets.UTF_8));

            // Get salt
            byte[] saltBytes = Arrays.copyOfRange(ctBytes, 8, 16);

            // Get ciphertext
            byte[] ciphertextBytes = Arrays.copyOfRange(ctBytes, 16, ctBytes.length);

            // Get key and iv from passphrase and salt
            byte[] key = new byte[keySize / 8];
            byte[] iv = new byte[ivSize / 8];
            EvpKDF(passphrase.getBytes(StandardCharsets.UTF_8), keySize, ivSize, saltBytes, key, iv);

            // Actual decrypt
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            byte[] recoveredPlaintextBytes = cipher.doFinal(ciphertextBytes);

            return new String(recoveredPlaintextBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 进行md5相关操作
     *
     * @param content
     * @return java.lang.String
     * @author Walter Hu
     * @date 2020/1/7
     * @since 1.8
     */
    public static String md5(String content) {
        return SecureUtil.md5(content);
    }

    @SuppressWarnings("unused")
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * @return a new pseudorandom salt of the specified length
     */
    private static byte[] generateSalt(int length) {
        Random r = new SecureRandom();
        byte[] salt = new byte[length];
        r.nextBytes(salt);
        return salt;
    }

    private static byte[] EvpKDF(byte[] password, int keySize, int ivSize, byte[] salt, byte[] resultKey, byte[] resultIv) throws NoSuchAlgorithmException {
        return EvpKDF(password, keySize, ivSize, salt, 1, "MD5", resultKey, resultIv);
    }

    private static byte[] EvpKDF(byte[] password, int keySize, int ivSize, byte[] salt, int iterations, String hashAlgorithm, byte[] resultKey, byte[] resultIv) throws NoSuchAlgorithmException {
        keySize = keySize / 32;
        ivSize = ivSize / 32;
        int targetKeySize = keySize + ivSize;
        byte[] derivedBytes = new byte[targetKeySize * 4];
        int numberOfDerivedWords = 0;
        byte[] block = null;
        MessageDigest hasher = MessageDigest.getInstance(hashAlgorithm);
        while (numberOfDerivedWords < targetKeySize) {
            if (block != null) {
                hasher.update(block);
            }
            hasher.update(password);
            block = hasher.digest(salt);
            hasher.reset();

            // Iterations
            for (int i = 1; i < iterations; i++) {
                block = hasher.digest(block);
                hasher.reset();
            }

            System.arraycopy(block, 0, derivedBytes, numberOfDerivedWords * 4,
                    Math.min(block.length, (targetKeySize - numberOfDerivedWords) * 4));

            numberOfDerivedWords += block.length / 4;
        }

        System.arraycopy(derivedBytes, 0, resultKey, 0, keySize * 4);
        System.arraycopy(derivedBytes, keySize * 4, resultIv, 0, ivSize * 4);

        return derivedBytes; // key + iv
    }
}
