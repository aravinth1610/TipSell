package org.tipSell.eSecurity.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tipSell.domain.enums.GrantTypes;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthRealm;
import org.tipSell.eSecurity.domain.repositoryDTO.OAuthClientRepositoryDTO;

import jakarta.transaction.Transactional;

@Repository
public interface OauthClientRepository extends JpaRepository<OauthClient, Long> {
	// UPDATE oauth_key_grant_type set auth_codes=34343434 WHERE
	// grant_type='AuthenticationCode' and client_fk=2;

	// select count(grant_type_id) from oauth_grant_type where
	// client_secret='2cyfCxeWMe74ug4JTTnfGdIfzxFD8pjXjoCWyiToC9f65xK2Xr1wXA==' and
	// client_fk=(select authkey_id from oauth_client where client_id='test-demo3');

//
//	
//	@Query(value = "SELECT auth_codes FROM oauth_key_grant_type WHERE client_fk=?1 AND client_secret=?2", nativeQuery = true)
//	String getAuthenticationCode(Long clientId, String clientSecret);
//
//	@Query(value = "SELECT grant_type FROM oauth_key_grant_type WHERE client_fk=?1 AND client_secret=?2", nativeQuery = true)
//	String getGrantType(Long clientId, String clientSecret);

	Boolean existsByClientID(String clientId);

	OauthClient findByClientID(String clientId);
	
	
	@Query("SELECT c.clientUid FROM OauthClient c WHERE c.clientID=:clientId")
	Long findClientUidByClientId(@Param(value="clientId") String clientId);
	
	default Long findData(String clientId) {
	  return findClientUidByClientId(clientId);
	}

	
//	OauthClient findByClientIDAndClientSecret(String clientId,String secret);

//    @Query("SELECT COUNT(g) FROM OauthClient ci WHERE ci.clientID = :clientId AND ci.realm = (SELECT r.realmUid FROM OauthRealm r WHERE r.realm = :realm)")
//    @Query("SELECT COUNT(ci) FROM OauthClient ci JOIN ci.realm ri WHERE ci.clientID=:clientId AND ri.realm=:realm")
	@Query(value  = "SELECT COUNT(client_uid) FROM oauth_client WHERE client_id = :clientId AND client_secret=:secret AND relam_fk = (SELECT realm_uid FROM oauth_realm WHERE realm = :realm)",nativeQuery = true)
	Integer existsByRealmAndClientIdAndSecret(@Param("clientId") String clientId,@Param("secret") String secret, @Param("realm") String realm);

//	@Query(value  = "SELECT COUNT(client_uid) FROM oauth_client WHERE client_id = :clientId AND relam_fk = (SELECT realm_uid FROM oauth_realm WHERE realm = :realm)",nativeQuery = true)
//	Integer existsByRealmAndClientId(@Param("clientId") String clientId, @Param("realm") String realm);

	@Query("SELECT COUNT(o.clientUid) FROM OauthClient o WHERE o.clientID=:clientId AND o.realm=:realm OR (:clientSecret IS NULL OR o.clientSecret = :clientSecret) OR (:grantType IS NULL OR o.grantType = :grantType)")
	Long existsClientUidByClientIdAndRealmAndGrantType(@Param("clientId") String clientId, @Param("realm") OauthRealm realm,@Param("clientSecret") String clientSecret, @Param("grantType") GrantTypes grantType);
 
	@Query("SELECT o.clientSecret AS clientSecret,o.grantType AS grantType FROM OauthClient o WHERE o.clientID=:clientId") 
	OAuthClientRepositoryDTO findSecretKeyByClient(@Param("clientId") String clientId);
	
	
	@Query(value  = "SELECT verify_mail FROM oauth_client WHERE client_id = :clientId AND relam_fk = (SELECT realm_uid FROM oauth_realm WHERE realm = :realm)",nativeQuery = true)
	Integer verifyMail(@Param("clientId") String clientId, @Param("realm") String realm);
	
	@Query("SELECT c.clientUid AS clientUid,c.clientID AS clientID, c.verifyMail AS verifyMail FROM OauthClient c WHERE c.realm=:realm")
	Set<OAuthClientRepositoryDTO> getAllClientByRealm(@Param("realm") OauthRealm realm);

	@Query("SELECT c.clientUid AS clientUid,c.clientID AS clientID, c.verifyMail AS verifyMail FROM OauthClient c WHERE c.clientUid=:clientUid AND c.realm=:realm")
	Optional<OAuthClientRepositoryDTO> clientByRealmAndClientUid(@Param("realm") OauthRealm realm, @Param("clientUid") Long clientUid);

	//c.updatedOn AS updatedOn,c.updatedBy AS updatedBy, c.deleteFlag AS deleteFlag
//	@Query("SELECT c.clientUid AS clientUid,  FROM OauthClient c WHERE c.clientUid=:clientUid") //  AND c.realm=:realm
//	Optional<OAuthClientRepositoryDTO> deleteClientByClientUid(@Param("clientUid") Long clientUid);

	
}
