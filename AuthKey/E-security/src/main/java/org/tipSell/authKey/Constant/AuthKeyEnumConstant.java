package org.tipSell.authKey.Constant;

public enum AuthKeyEnumConstant {

	TOKEN_SECRET_KEY("J4auz1eRMGszFtvpIAALRVcLqw+DeHp8QKYTpME33iQ="),
	CRYPT_SECURE_KEY("2ck6gxUSAouHAgS6uFiftm0oatC64H7MYB3dIl/H9JA="),
	ENCRYPTION_ALGORITHM("AES"),
	ENCRYPTION_TRANSFORMATION("AES/GCM/NoPadding"), 
	GCM_TAG_LENGTH(16), 
	GCM_IV_LENGTH(12),
	KEY_SIZE(256);

	String value;

	int numericValue;

	private AuthKeyEnumConstant(String value) {
		this.value = value;
	}

	private AuthKeyEnumConstant(int numericValue) {
		this.numericValue = numericValue;
	}

	public String getValue() {
		return value;
	}

	public int getNumericValue() {
		return numericValue;
	}
	
}
