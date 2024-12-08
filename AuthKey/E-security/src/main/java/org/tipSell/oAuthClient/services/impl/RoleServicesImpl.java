package org.tipSell.oAuthClient.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthRealm;
import org.tipSell.eSecurity.domain.entity.Roles;
import org.tipSell.eSecurity.domain.repository.OauthClientRepository;
import org.tipSell.eSecurity.domain.repository.OauthRealmRepository;
import org.tipSell.eSecurity.domain.repository.RolesRepository;
import org.tipSell.eSecurity.domain.repositoryDTO.RolesRepositoryDTO;
import org.tipSell.eSecurity.mapper.RoleMapper;
import org.tipSell.eSecurity.payload.request.RoleRequest;
import org.tipSell.oAuthClient.services.RoleService;
import org.tipSell.uniCore.customeExceptions.CommonCaseException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RoleServicesImpl implements RoleService {

	private final RolesRepository rolesRepository;
	private final OauthClientRepository oAuthClientRepo;

	@PersistenceContext
	private EntityManager entityManager;

	private final RoleMapper roleMapper;

	private Long client(String clientId) {
		return oAuthClientRepo.findClientUidByClientId(clientId);
	}

	@Override
	public void createRole(Long clientUid, Set<RoleRequest> roles) {

		List<Roles> rolesEntities = roleMapper.rolesMapperList(roles, clientUid);

		rolesRepository.saveAll(rolesEntities);

	}

	@Override
	public Set<RolesRepositoryDTO> allRolesByClientId(Long clientUid) {

		return rolesRepository.findByClientId(new OauthClient(clientUid));
	}

	@Override
	public RolesRepositoryDTO roleByUid(Long clientUid, Long roleUid) {

		return rolesRepository.findByRoleUid(new OauthClient(clientUid), roleUid)
				.orElseThrow(() -> new CommonCaseException("Role with ID " + roleUid + " not found."));
	}

	@Override
	public void updateRoleByClient(Long roleUid, RoleRequest roles) {
		roles.setRoleUid(roleUid);
		System.out.println(roles);
		Roles role = roleMapper.roleMapper(roles);
		rolesRepository.save(role);
	}

	@Override
	public void deleteRole(Long roleUid) {
//		RoleRequest roles = new RoleRequest(roleUid, 1);
//		Roles rolesEntities = roleMapper.roleMapper(roles);
//
//		rolesRepository.save(rolesEntities);
	}
}
