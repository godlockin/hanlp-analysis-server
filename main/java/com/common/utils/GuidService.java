package com.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

@Slf4j
@Component
public class GuidService {

    private final static String[] hexDigits = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    private static MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error happened when we load MD5 method" + e);
        }
    }

    synchronized public static String getGuid(String base, String prefix, boolean isTimeSensitive) {

        String baseStr = base + ((isTimeSensitive) ? System.currentTimeMillis() : "");
        String trgt = getMd5(baseStr);
        StringBuilder sb = new StringBuilder();
        sb.append("sc").append("_");

        if (StringUtils.isNotBlank(prefix)) {
            sb.append(prefix).append("_");
        }

        sb.append(baseStr).append("_");

        if (isTimeSensitive) {
            sb.append(System.currentTimeMillis()).append("_");
        }

        sb.append(trgt);
        return sb.toString().trim().toLowerCase();
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder resultSb = new StringBuilder();
        for (byte b : bytes) {
            resultSb.append(byteToHexString(b));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        n = (n < 0) ? 256 + n : n;
        return hexDigits[n / 16] + hexDigits[n % 16];
    }

    synchronized public static String getMd5(String base) {
        return StringUtils.isBlank(base) ? base : byteArrayToHexString(md5.digest(base.trim().getBytes()));
    }

    public static String getGuid(String base) {
        return getGuid(base, "", false);
    }
}
