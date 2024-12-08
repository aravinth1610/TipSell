package org.tipSell.eSecurity.domain.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tipSell.eSecurity.domain.entity.Attribute;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.repositoryDTO.AttributeRepositoryDTO;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute,Long> {

	@Query("SELECT a.attributeUid AS attributeUid,a.key AS key,a.value AS value FROM Attribute a WHERE a.client=:client")
	Set<AttributeRepositoryDTO> findAttributeByClient(@Param(value="client") OauthClient client);
	
	@Query("SELECT a.attributeUid AS attributeUid,a.key AS key,a.value AS value FROM Attribute a WHERE a.client=:client AND a.attributeUid=:attributeUid")
	AttributeRepositoryDTO findAttributeByClientAndUid(@Param(value="client") OauthClient client, @Param(value="attributeUid") Long attributeUid);
	
}
