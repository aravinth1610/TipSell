package org.tipSell.eSecurity.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tipSell.eSecurity.domain.entity.User;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.repositoryDTO.UserRepositoryDTO;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	@Modifying
	@Transactional
	@Query("UPDATE User c set c.authenticationCode=:authenticationCode WHERE c.userUid=:customerId AND c.client=:client")
	Integer updateAuthenticationCode(@Param(value="authenticationCode") String authenticationCode,@Param(value="customerId") Long customerId,@Param(value="client") OauthClient client);
	
	User findByMail(String mail);
	   
	@Query("SELECT u.userUid AS uid,u.roles AS roles FROM User u WHERE u.authenticationCode=:authenticationCode")
	UserRepositoryDTO findUidAndRolesByAuthenticationCode(@Param(value="authenticationCode") String authenticationCode);
		
	Boolean existsByMail(String clientId);

	@Query("SELECT u.userUid AS uid,u.password AS password FROM User u WHERE (:user IS NULL OR u.mail = :user) OR (:user IS NULL OR u.userName = :user)")
	UserRepositoryDTO findUserUidByUser(@Param(value="user") String user);

//	@Query("SELECT u.userUid AS uid,u.password AS password,u.roles AS roles FROM User u WHERE u.userName=:userName")
//	UserRepositoryDTO findUserUidByUserName(@Param(value="userName") String userName);

	
}
