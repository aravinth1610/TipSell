package org.tipSell.domain.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author aravinth
 * @since 2024
 *
 *        A sample source file for the code formatter preview
 */
public enum GrantTypes {

	AuthorizationCode("authorization_code"), ClientCredential("client_credential"),Password("password"), RefreshToken("refresh_token");

	private String value;

	GrantTypes(String value) {
		this.value = value;
	}

	public String getGrantValue() {
		return value;
	}

	public static GrantTypes fromGrantType(String grantType) {
		GrantTypes grantTypeValue = null;
		for (GrantTypes authType : GrantTypes.values()) {
			if (authType.value.equalsIgnoreCase(grantType)) {
				grantTypeValue = authType;
				return grantTypeValue;
			}
		}
		return grantTypeValue;
	}

	public static List<String> getAllGrantValues() {
		List<String> values = new ArrayList<>();
		for (GrantTypes authType : GrantTypes.values()) {
			values.add(authType.getGrantValue());
		}
		return values;
	}
	
}
