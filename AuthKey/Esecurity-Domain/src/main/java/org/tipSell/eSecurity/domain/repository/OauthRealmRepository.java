package org.tipSell.eSecurity.domain.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthRealm;
import org.tipSell.eSecurity.domain.repositoryDTO.OauthRealmRepositoryDTO;

import jakarta.transaction.Transactional;

@Repository
public interface OauthRealmRepository extends JpaRepository<OauthRealm, Long> {

//	Boolean existsByAuthKeyId(Long authKeyId);
//
//	Boolean existsByClientId(String clientId);
//
//	OauthClient findByAuthKeyId(Long clientId);
//
//
//	@Query(value = "SELECT authkey_id FROM oauth_client WHERE client_id=?1", nativeQuery = true)
//	Long getAuthIdByClientId(String clientId);
     
	Boolean existsByRealm(String realm);
	
//	OauthRealm findByRealm(String realm);
	
//	OauthRealm getByRealm(String realm);
	
	@Query("SELECT r.realmUid FROM OauthRealm r WHERE realm=:realm")
	Long findRealmUidByRealm(@Param(value="realm") String realm);
		
	@Query("SELECT r.realmUid AS realmUid, r.realm AS realm FROM OauthRealm r")	
	Set<OauthRealmRepositoryDTO> getAllRealmUidAndRealm(); 
	
	@Query("SELECT r.realmUid AS realmUid, r.realm AS realm FROM OauthRealm r WHERE realmUid=:realmUid")	
	Optional<OauthRealmRepositoryDTO> getRealmByReamUid(@Param(value="realmUid") Long realmUid); 

	
//	@Modifying
//	@Transactional
//	@Query("UPDATE OauthRealm r set r.deleteFlag=1 WHERE r.realmUid=:realmUid")
//	Optional<Integer> deleteRealm(@Param(value="realmUid") Long realmUid);
	
}
