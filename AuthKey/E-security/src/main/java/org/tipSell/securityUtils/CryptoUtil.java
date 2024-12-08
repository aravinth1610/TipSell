package org.tipSell.securityUtils;

import static org.tipSell.authKey.Constant.AuthKeyConstant.CODE_VALIDITY;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.tipSell.domain.enums.Unit;
import org.tipSell.uniCore.securityConstant.SecurityConstant;
import org.tipSell.uniCore.securityConstant.SecurityMessages;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Author aravinth
 * @since 2024
 *
 *        A sample source file for the code formatter preview
 */
@Component
public class CryptoUtil {

	private static String cryptSecureKey;

	@Value("${crypto.secureKey}")
	public void setCryptSecureKey(String secureKey) {
		cryptSecureKey = secureKey;
	}

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	private static final String ENCRYPTION_ALGORITHM = "AES";
	private static final String ENCRYPTION_TRANSFORMATION = "AES/GCM/NoPadding";
	private static final int GCM_TAG_LENGTH = 16;
	private static final int GCM_IV_LENGTH = 12;
	private static final int KEY_SIZE = 256;

	
	public static SecretKey generateKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
		keyGenerator.init(KEY_SIZE, new SecureRandom());
		return keyGenerator.generateKey();
	}

	private static String cryptoEncrypt(String data, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION, "BC");
		byte[] iv = new byte[GCM_IV_LENGTH];
		SecureRandom random = new SecureRandom();
		random.nextBytes(iv);
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
		cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);

		byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
		byte[] encryptedDataWithIv = new byte[iv.length + encryptedData.length];
		System.arraycopy(iv, 0, encryptedDataWithIv, 0, iv.length);
		System.arraycopy(encryptedData, 0, encryptedDataWithIv, iv.length, encryptedData.length);

		return Base64.getUrlEncoder().withoutPadding().encodeToString(encryptedDataWithIv);

	}

	private static String cryptoDecrypt(String encryptedData, SecretKey key) throws Exception {
		byte[] encryptedDataWithIv = Base64.getUrlDecoder().decode(encryptedData);
		byte[] iv = new byte[GCM_IV_LENGTH];
		byte[] encryptedDataBytes = new byte[encryptedDataWithIv.length - GCM_IV_LENGTH];

		System.arraycopy(encryptedDataWithIv, 0, iv, 0, iv.length);
		System.arraycopy(encryptedDataWithIv, iv.length, encryptedDataBytes, 0, encryptedDataBytes.length);

		Cipher cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION, "BC");
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
		cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);

		byte[] decryptedData = cipher.doFinal(encryptedDataBytes);

		return new String(decryptedData, StandardCharsets.UTF_8);
	}

	public static String encodeKey(SecretKey key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	private static SecretKey decodeKey(String encodedKey) {
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, ENCRYPTION_ALGORITHM);
	}

	public static <T> String encrypt(T data) {
		SecretKey retrievedKey = decodeKey(cryptSecureKey);
		try {
			String dataStr;
			if (data instanceof String) {
				dataStr = (String) data;
			} else {
				ObjectMapper objectMapper = new ObjectMapper();
				dataStr = objectMapper.writeValueAsString(data);
			}
			return cryptoEncrypt(dataStr, retrievedKey);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public static <T> String decrypt(T data) {
		SecretKey retrievedKey = decodeKey(cryptSecureKey);
		try {
			String dataStr;
			if (data instanceof String) {
				dataStr = (String) data;
			} else {
				ObjectMapper objectMapper = new ObjectMapper();
				dataStr = objectMapper.writeValueAsString(data);
			}
			return cryptoDecrypt(dataStr, retrievedKey);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public static String generateRandomKeyWithExpireDate() {
		StringBuffer code = new StringBuffer();
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[32];
		random.nextBytes(bytes);
		code.append(Base64.getUrlEncoder().withoutPadding().encodeToString(bytes));
		code.append("/.+./");
		Date expiryDate = TokenUtil.calculateLifeSpan(CODE_VALIDITY,Unit.days.getTokeUnitValue());
        LocalDateTime expiryDateTime = expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		code.append(expiryDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
		return code.toString();
	}

	public static boolean validateRandomKeyExpiration(String keyWithExpiration) {
		System.out.println(keyWithExpiration);
		try {
			String[] parts = keyWithExpiration.split("/.+./");

			if (parts.length != 2) {
				throw new AccessDeniedException(SecurityMessages.ACCESS_DENIED.message());
			}

			String expirationDateString = parts[1];
			System.out.println(parts[1]);
			LocalDateTime expirationDate = LocalDateTime.parse(expirationDateString, DateTimeFormatter.ISO_DATE_TIME);
			System.out.println(expirationDate);
			LocalDateTime currentDate = LocalDateTime.now();
			return currentDate.isBefore(expirationDate);

		} catch (DateTimeParseException e) {
			throw new AccessDeniedException(SecurityMessages.ACCESS_DENIED.message(), e);
		}
	}

}
