package org.tipSell.eSecurity.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tipSell.eSecurity.domain.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

}
