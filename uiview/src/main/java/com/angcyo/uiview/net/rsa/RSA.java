package com.angcyo.uiview.net.rsa;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：红鸟RSA加密算法工具,可以正常使用
 * 创建人员：Robi
 * 创建时间：2016/12/12 16:49
 * 修改人员：Robi
 * 修改时间：2016/12/12 16:49
 * 修改备注：
 * Version: 1.0.0
 */
public class RSA {

    static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+OdspoCjCgBf/jv+Sdx3mP+JIC1i8YCi+jU6rkeBNWOTfFovA3HSk1ODZ0shWD+E0dRTMlJmFpgffFqbytsRJPsDieeNgP/0psJsejOogG6S+lSZgWAnrxRWYpno8Z5CSgBWWgXHjPGN9u22zc23TFoxjcZUjvee4AM72It/9dQIDAQAB";

    /**
     * 加密
     */
    public static String encode(String data) {
        String encode = "";
        byte[] encodedData;
        try {
            encodedData = RSAUtils.encryptByPublicKey(data.getBytes(), PUBLIC_KEY);
            encode = Base64Utils.encode(encodedData);
        } catch (Exception e) {
        }
        return encode.replaceAll("\\n", "");
    }

    /**
     * 解密
     */
    public static String decode(String encode, String privateKey) {
        String target = "";
        byte[] decodedData;
        try {
            decodedData = RSAUtils.decryptByPrivateKey(Base64Utils.decode(encode), privateKey);
            target = new String(decodedData);
        } catch (Exception e) {
        }
        return target;
    }
}
