package org.tipSell.eSecurity.domain.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.Roles;
import org.tipSell.eSecurity.domain.repositoryDTO.RolesRepositoryDTO;

import jakarta.transaction.Transactional;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {

	@Query("SELECT r.roleUid AS roleUid,r.role AS role,r.defaultRole AS defaultRole FROM Roles r WHERE r.client=:client")
	Set<RolesRepositoryDTO> findByClientId(@Param(value = "client") OauthClient client);
	
	@Query("SELECT r.roleUid AS roleUid,r.role AS role,r.defaultRole AS defaultRole FROM Roles r WHERE r.client=:client AND r.roleUid=:roleUid")	
	Optional<RolesRepositoryDTO> findByRoleUid(@Param(value = "client") OauthClient client,@Param(value = "roleUid") Long roleUid);
		
	@Query("SELECT r.roleUid FROM Roles r WHERE r.roleUid=:roleUid")
	Long findRoleUidByRoleUid(@Param(value="roleUid") Long roleUid);

	
    @Query("SELECT r.defaultRole FROM Roles r WHERE r.defaultRole = true AND r.client=:client")
    Optional<Boolean> findDefaultRolesTrue(@Param(value = "client") OauthClient client);
	
}
