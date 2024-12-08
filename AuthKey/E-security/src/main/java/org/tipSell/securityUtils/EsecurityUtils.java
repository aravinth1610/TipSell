package org.tipSell.securityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.tipSell.eSecurity.domain.services.OauthClientDomain;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EsecurityUtils {

	public static Map<String, String> decryptClientAndSecret(String cisiKey) {
		String clientAndSecretId = CryptoUtil.decrypt(cisiKey);
		String[] splitByDot = clientAndSecretId.split("\\.");

		if (splitByDot.length != 2) {
			throw new IllegalArgumentException("Invalid decrypted string format. Expected format: 'clientId.secretId'");
		}

		Map<String, String> cisiValues = new HashMap<>();
		cisiValues.put("clientId", splitByDot[0]);
		cisiValues.put("secretId", splitByDot[1]);

		return cisiValues;
	}

	public static String generateClientSecret() {
		String randomKey = UUID.randomUUID().toString().substring(0, 12);
		return CryptoUtil.encrypt(randomKey);
	}

}
