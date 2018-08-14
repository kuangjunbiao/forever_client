package com.gaoan.forever.utils.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RSAUtils {

	private static Log logger = LogFactory.getLog(RSAUtils.class);

	private static Map<String, String> paramMap = new HashMap<String, String>();

	private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp8hCIQ/P1kY1IGj7Kw3JdeJaaE0gtn6m6zNrbBovnEdMwjlle4Ex/Q1vy0Zh87ogUHUz8UY2JqjGgJKzXltMhSFeYTlO+uEGhyqKgUZvKDALsIJdAhIa9a6W4E+j8QUpjDUcs7IOUTr1BcWkaZCDxpupDBB2UAHBjkra/EFC8wXmP2oYz9sj1D7hYWLSeiKQSn5lgx8r0nmM981oEwFtTeJtG7I3ebu2EyGpfhJiLko+5j074m4stKvGKK8boS3DZOjj8974TEiDtXZafaFivs6avPQfChDes25RxLDDOoUnNhMOrW5fLgh4OGhfu/mhJcO3tLkXgyywY1JTcHigmQIDAQAB";
	private static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnyEIhD8/WRjUgaPsrDcl14lpoTSC2fqbrM2tsGi+cR0zCOWV7gTH9DW/LRmHzuiBQdTPxRjYmqMaAkrNeW0yFIV5hOU764QaHKoqBRm8oMAuwgl0CEhr1rpbgT6PxBSmMNRyzsg5ROvUFxaRpkIPGm6kMEHZQAcGOStr8QULzBeY/ahjP2yPUPuFhYtJ6IpBKfmWDHyvSeYz3zWgTAW1N4m0bsjd5u7YTIal+EmIuSj7mPTvibiy0q8YorxuhLcNk6OPz3vhMSIO1dlp9oWK+zpq89B8KEN6zblHEsMM6hSc2Ew6tbl8uCHg4aF+7+aElw7e0uReDLLBjUlNweKCZAgMBAAECggEACz0o4NoUTAAr8t3VZU7pHdJpx+7SsCbPu5WYLoeahNdp1uDNYXaICLFruawfR5pCK+GN9y2C1xgbI+WvyjRHbeuMTOrv1XJZY5h/6qTzMTf2sH4rCMZ5s9dcF+59xtlXrSKpp4fX2a0fWKGin8ekbBtlOX+ozTcw5f0xqhAdEfZJb2XKkfPvWcc3I6vgCJ6RKf0PMpu0Q16RwRpd0beKWgsci7T2yIjGuOMT8ojqV0JznNKVh1qdjABmIVevki6A0EwjhjIHuUYHtFCha5Gr5G/dnT0U4u/MaUIJc0CUmhbgHGCqmdBPTohuXk65gVnVgFDvJkpDr015zBheQOHTWQKBgQDSLqgdW2Umx+DoymhPLXQ11Ss81X9lHh2Oo4OQjeN+udvBbXZ1yCa49CQ6N1eBB57GNGUI3a4pXoaCrcIPnmC6uYCgradOpZzbWtnG9kS+cDnZUKNblg/H4xavoY9Hy+wF1KIn3jv7wlxj1fCJ6xmnXemdTizT3cx62ZjSRYB5OwKBgQDMW3H6Ef8vNyMFIkprtjHsVBKYE/i2jBLrGsiQQUa/fAgnBqqkVR8A/UvCsDqJb6IXHmoU0LmpnPE9UF4BwV4ttfcG11RbqsRTbZpCV/JsqzLI0qzGt7nAGNv3M3T/LvMKGTPZBOjY0QmuDuXxkbNftJU066fKQsHDYi2HoEsQOwKBgB84jQJnXBwpnrAVuO7h+pwwgQ61TAoyMgPJKDblzMA7TXhrESinxZC9u0/mfpoNDrm0eLmbbE562xI4S1ZUmVvDSGcVxH97PFSdJzJVzJpZQ06TmuB05v0zL2CBaC4rDXFER54SdeiM03qFAgkKojHpUlVqkmAaV1B8WCYL6uyXAoGBAIvpC6afDawAvsw0BumpqpJXmQdfnQc17XnjM294EXzl6Rr2+Htb/8cIpAbd6qXPtRTI8O3hXUuftJY64LGo9taq3z5izFGaH6HSxysm1UXLDBDsE/hVLyoymt6q7y4KscC3MoUhVF2k+FCfyJRUIY+jrge2U4EppUkkfXF+sAWjAoGAM56+7uLuEs1DkZB0YGo7ubX8Jj0wGu3y/HTFXMP4WyugO/P1CQdeWWeAKKppVekORZ+HMQAUWLt3Qn7Se8/puhiRfqRzUsXf+CG4YYWN/aUWVEZda1HjBAt6mw/nMRsT0bJPBIkfnaIguZRE+d9BlhJKqWEh3Lw9yiu8quiD74Q=";

	public static String getData(String key, String encryptStr) {
		String value = paramMap.get(key);
		if (StringUtils.isNotEmpty(value)) {
			return value;
		}
		value = decrypt(encryptStr, PRIVATE_KEY);
		paramMap.put(key, value);
		return value;
	}

	/**
	 * 生成公私钥
	 * 
	 * @throws Exception
	 */
	public static void genKey() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			logger.info("publicKey = " + Base64.encodeBase64String(publicKey.getEncoded()));
			logger.info("privateKey = " + Base64.encodeBase64String(privateKey.getEncoded()));
		} catch (Exception e) {
			logger.error("genKey is error, " + e.getMessage());
		}
	}

	/**
	 * 加密
	 * 
	 * @param content
	 * @param publicStr
	 * @throws Exception
	 */
	public static String encrypt(String content, String publicStr) {
		String result = "";
		try {
			/* X.509 标准中定义的公钥编码标准，使用这个类进行转换 */
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicStr));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// 获得公钥对象
			PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
			/* 使用Cipher加密 */
			/* 定义加密方式 */
			Cipher cipher = Cipher.getInstance("RSA");
			/* 使用公钥和加密模式初始化 */
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			/* 获取加密内容以UTF-8为标准转化的字节进行加密后再使用base64编码成字符串 */
			/* 加密后的字符串 */
			result = Base64.encodeBase64String(cipher.doFinal(content.getBytes("UTF-8")));
		} catch (Exception e) {
			logger.error("encrypt is error, " + e.getMessage());
		}

		return result;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 * @param privateStr
	 * @throws Exception
	 */
	public static String decrypt(String content, String privateStr) {
		String result = "";
		try {
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateStr));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			/* 解密后的内容 */
			result = new String(cipher.doFinal(Base64.decodeBase64(content)), "UTF-8");
		} catch (Exception e) {
			logger.error("decrypt is error, " + e.getMessage());
		}
		return result;

	}

	public static void main(String[] args) {
		// genKey();
		System.out.println(encrypt("80", PUBLIC_KEY));
		System.out.println(decrypt(encrypt("80", PUBLIC_KEY), PRIVATE_KEY));
	}
}
