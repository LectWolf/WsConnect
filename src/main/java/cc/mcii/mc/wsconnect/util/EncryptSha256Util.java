package cc.mcii.mc.wsconnect.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


/**
 * EncryptSha256Util SHA256加密
 *
 * @author LectWolf
 * @version 2024/02/17 20:10
 **/
public class EncryptSha256Util {


    /**
     * 生成盐值
     *
     * @param n 盐值长度
     * @return 盐值
     */
    public static String getSalt(int n) {
        char[] chars = ("0123456789abcdefghijklmnopqrstuvwxyz").toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }


    /**
     * SHA256加密
     *
     * @param str 加密字符串
     * @return SHA256字符串
     */
    public static String getSha256Str(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }


    /**
     * SHA256加密 将byte转16进制
     *
     * @param bytes 字节码
     * @return 加密后的字符串
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

}

