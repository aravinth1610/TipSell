package org.tipSell.grantTypeFactoryImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tipSell.grantTypeFactory.GrantTypeFactory;

import lombok.val;

@Component
public class GrantTypeValidatorFactory {

	private final Map<String, GrantTypeFactory> validators;
	
	private GrantTypeValidatorFactory(List<GrantTypeFactory> handlerList) {
		validators = handlerList.stream().collect(
				Collectors.toMap(handler -> handler.getClass().getSimpleName().replace("GrantFactory","").toLowerCase(), handler -> handler));

	}

	public GrantTypeFactory getValidator(String grantType) {
		return validators.get(grantType.toLowerCase());
	}

}
