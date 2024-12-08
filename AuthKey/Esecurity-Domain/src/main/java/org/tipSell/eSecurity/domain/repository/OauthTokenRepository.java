package org.tipSell.eSecurity.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthToken;
import org.tipSell.eSecurity.domain.repositoryDTO.OauthTokenRepositoryDTO;

@Repository
public interface OauthTokenRepository extends JpaRepository<OauthToken, Long> {

	@Query("SELECT t.accessTokenExpiration AS accessTokenExp, t.accessTokenUnit AS accessTokenUnit, t.refreshTokenExpiration AS refreshTokenExp, t.refreshTokenUnit AS refreshTokenUnit FROM OauthToken t WHERE t.client=:client")
	Optional<OauthTokenRepositoryDTO> findAccessAndRefrershTokenByClient(@Param(value = "client") OauthClient client);

	@Query("SELECT u.tokenUid FROM OauthToken u WHERE u.client=:client")	
	Optional<Long> findTokenUidByClientFk(@Param(value = "client") OauthClient client);
	
}
