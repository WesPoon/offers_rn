package com.offers_rn.utility;



import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by user on 3/8/2017.
 */
public class AES_CBC_PKCS5PADDING {

    public static byte[] Encrypt(SecretKey secretKey, byte[] iv, String msg) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        System.out.println("AES_CBC_PKCS5PADDING IV:"+cipher.getIV());
        System.out.println("AES_CBC_PKCS5PADDING Algoritm:"+cipher.getAlgorithm());
        byte[] byteCipherText = cipher.doFinal(msg.getBytes("UTF-8"));
        System.out.println("加密結果的Base64編碼：" + Base64.encodeToString(byteCipherText,0));

        return byteCipherText;
    }

    public static void Decrypt(SecretKey secretKey, byte[] cipherText, byte[] iv) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] decryptedText = cipher.doFinal(cipherText);
        String strDecryptedText = new String(decryptedText);
        System.out.println("解密結果：" + strDecryptedText);
    }
}
