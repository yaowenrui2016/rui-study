package indi.rui.study.unittest.util;

import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

/**
 * @author: yaowr
 * @create: 2021-11-08
 */
public class RsaHelper {

    private static final String RSA = "RSA";

    private static final String CHARSET = "UTF-8";

    /**
     * 公钥加密
     *
     * @param pubKey
     * @param inputStr
     * @return
     */
    public static String encode(byte[] pubKey, String inputStr) {
        String encStr = null;
        try {
            RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(pubKey));
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encStr = Base64.getEncoder().encodeToString(cipher.doFinal(inputStr.getBytes(CHARSET)));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return encStr;
    }

    /**
     * 私钥解密
     *
     * @param priKey
     * @param inputStr
     * @return
     */
    public static String decode(byte[] priKey, String inputStr) {
        String decStr = null;
        try {
            byte[] inputBytes = new BASE64Decoder().decodeBuffer(inputStr);
            PrivateKey privateKey = KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(priKey));
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decStr = new String(cipher.doFinal(inputBytes));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decStr;
    }

    public static void main(String[] args) {
        String inputStr = "bU/J9yNBcTPkubtkrTcPQPXfn2Jk4WtZIdy4tyoRb7K3Q0AF+Yo9A/aIgsm+gV+UTK/lh8rSHc76sNYpLRaShkFGdzie0ISQKxTefasCL8yb1+TLJKUIWNAUQIC+hLdyVEk2rGPYwbsYjkfzqNKweoRU/d7iMIo0Z27b5xdCcSo=";
        String priKey = FileUtils.readFileToString(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                        "keypair/prikey.txt")).getFile(), "utf-8");
        System.out.println("input: " + inputStr + "\ndecode: "
                + decode(Hex.decode(priKey), inputStr));
    }
}
