package org.tipSell.validations.Services;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tipSell.configure.UserServices;
import org.tipSell.domain.enums.GrantTypes;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthRealm;
import org.tipSell.eSecurity.domain.repository.UserRepository;
import org.tipSell.eSecurity.domain.repository.AttributeRepository;
import org.tipSell.eSecurity.domain.repository.OauthClientRepository;
import org.tipSell.eSecurity.domain.repository.OauthRealmRepository;
import org.tipSell.eSecurity.domain.repository.RolesRepository;
import org.tipSell.eSecurity.domain.repositoryDTO.UserRepositoryDTO;
import org.tipSell.eSecurity.payload.request.AttributeRequest;
import org.tipSell.eSecurity.payload.request.RoleRequest;
import org.tipSell.securityUtils.EsecurityUtils;
import org.tipSell.uniCore.customeExceptions.CommonCaseException;
import org.tipSell.uniCore.customeExceptions.CommonCaseValidatorException;
import org.tipSell.uniCore.securityConstant.SecurityConstant;
import org.tipSell.uniCore.securityConstant.SecurityMessages;

import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class RequestValidationsServices {

	private final UserServices userServies;

	private final OauthRealmRepository oAuthRealmRepo;

	private final OauthClientRepository oathKeyClientRepo;

	private final UserRepository customerRepo;

	private final RolesRepository roleRepo;
	
	private final AttributeRepository attributeRepo;
	
	private void validateRolesSet(Set<RoleRequest> roles, OauthClient client) {
		Map<String, List<String>> errors = new HashMap<String, List<String>>();
		long defaultRoleCount = roles.stream().filter(role -> Boolean.TRUE.equals(role.getDefaultRole())).count();
		if (isDefaultRoleExists(client)) {
			if (defaultRoleCount > 0) {
				errors.put("Roles", Arrays.asList("A default role already exists. You cannot add another."));
			}
		} else {
			if (defaultRoleCount != 1) {
				errors.put("Roles", Arrays.asList("There must be exactly one default role."));
			}
		}
		
		if (!errors.isEmpty()) 
		throw new CommonCaseValidatorException(errors);
	}

	private void validateSingleRole(RoleRequest role, OauthClient client) {
		Map<String, List<String>> errors = new HashMap<String, List<String>>();
		if (role.getDefaultRole() == null || !Boolean.TRUE.equals(role.getDefaultRole())) {
			errors.put("Roles", Arrays.asList("A single role must be marked as default."));
		} else {
			if (isDefaultRoleExists(client)) {
				errors.put("Roles", Arrays.asList("A default role already exists. You cannot add another."));
			}
		}
		
		if (!errors.isEmpty()) 
		throw new CommonCaseValidatorException(errors);
	}

	public boolean isValue(Object value) {

		if (value == null) {
			return false;
		}

		if (value instanceof String) {
			return !((String) value).trim().isEmpty();
		} else if (value instanceof Collection) {
			return !((Collection<?>) value).isEmpty();
		} else if (value instanceof Map) {
			return !((Map<?, ?>) value).isEmpty();
		} else if (value instanceof Integer) {
			return (Integer) value != 0;
		}
		return true;
	}

	public boolean isRealmExists(String realm) {
		return oAuthRealmRepo.existsByRealm(realm);
	}

	public boolean isClientIdExists(String clientId) {
		return oathKeyClientRepo.existsByClientID(clientId);
	}

	public boolean isGrantTypeExists(String grantType) {
		boolean isAuthType = false;
		for (GrantTypes authType : GrantTypes.values()) {
			if (authType.getGrantValue().equalsIgnoreCase(grantType)) {
				isAuthType = true;
				return isAuthType;
			}
		}
		return isAuthType;
	}

	public void isRealmRequest(String realm, String clientID) {

		if (isRealmExists(realm)) {
			throw new CommonCaseException("Realm is Already Exists.");
		} else if (isClientIdExists(clientID)) {
			throw new CommonCaseException("Client ID is Already Exists.");
		}
//		else if (!isGrantTypeExists(grantType)) {
//			throw new CommonCaseException("Illegal Grant-Type " + grantType + "not found");
//		}
	}

	public void isClientsRequest(String realm, String clientID) {

		if (!isRealmExists(realm)) {
			throw new CommonCaseException("Realm is Not Exists,Please Check the Realm.");
		} else if (isClientIdExists(clientID)) {
			throw new CommonCaseException("Client ID is Already Exists.");
		}
//		} else if (!isGrantTypeExists(grantType)) {
//			throw new CommonCaseException("Illegal Grant-Type " + grantType + "not found");
//		}
	}

	public void isRealmExistsForClientIdAndSecret(String realm, String clientId, String secretKey, String grantType) {
		try {
			GrantTypes grantTypeEnum = GrantTypes.fromGrantType(grantType);
			Long realmUid = oAuthRealmRepo.findRealmUidByRealm(realm);
			Long isRealmExistsForClientId = oathKeyClientRepo.existsClientUidByClientIdAndRealmAndGrantType(clientId,
					new OauthRealm(realmUid), secretKey, grantTypeEnum);
			if (isRealmExistsForClientId <= 0) {
				throw new CommonCaseException("Invalid Realm or Cisi key, Please verify the Realm or Cisi key.");
			}
		} catch (Exception e) {
			throw new CommonCaseException("Invalid Realm or Cisi key, Please verify the Realm or Cisi key.");
		}
	}

	public UserRepositoryDTO isAuthorizedUserExists(String userName, String password, String userType) {
		UserRepositoryDTO user = userServies.loadUserByUsername(userName, userType);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(password, user.getPassword()))
			throw new AccessDeniedException(SecurityMessages.ACCESS_DENIED.message());

		return user;
	}

	public Map<String, String> isCisiKeyExists(String realm, String cisiKey, String grantType) {
		Map<String, String> cisiValues = EsecurityUtils.decryptClientAndSecret(cisiKey);
		isRealmExistsForClientIdAndSecret(realm, cisiValues.get("clientId"), cisiValues.get("secretId"), grantType);

		return cisiValues;
	}

	public boolean isMailValid(String mail) {
		Pattern pattern = Pattern.compile(SecurityConstant.EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(mail);
		return matcher.matches() && (mail.length() >= 6 && mail.length() <= 74);

	}

	public boolean isPasswordConfirmPasswordMatched(String password, String confirmPassword) {
		return (password != null && confirmPassword != null && password.equals(confirmPassword));
	}

	public boolean isMailExists(String mail) {
		return customerRepo.existsByMail(mail);
	}

	public boolean isDefaultRoleExists(OauthClient client) {
		return roleRepo.findDefaultRolesTrue(client).isPresent();
	}

	public void roleExists(Object value, Long clientUid) {

		OauthClient client = new OauthClient(clientUid);

		if (value instanceof Set) {
			@SuppressWarnings("unchecked")
			Set<RoleRequest> roles = (Set<RoleRequest>) value;

			validateRolesSet(roles, client);
		} else if (value instanceof RoleRequest) {
			RoleRequest role = (RoleRequest) value;
			validateSingleRole(role, client);
		}
	}

	public void attributeExists(Set<AttributeRequest> value, Long clientUid) {
		OauthClient client = new OauthClient(clientUid);
		Map<String, List<String>> errors = new HashMap<String, List<String>>();
		System.out.println(value.size() < 5);
		System.out.println(attributeRepo.findAttributeByClient(client).size() < 5);
		if(value.size() > 5 && attributeRepo.findAttributeByClient(client).size() < 5) {
			errors.put("Attribute", Arrays.asList("Attribute Key and Value maximun FIVE Only."));
		}
		
		if (!errors.isEmpty())
		throw new CommonCaseValidatorException(errors);
	}
	

}
