package org.tipSell.configure;

import static org.tipSell.authKey.Constant.AuthKeyConstant.CLIENT_USER;
import static org.tipSell.authKey.Constant.AuthKeyConstant.AUTHKEY_USER;


import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.tipSell.eSecurity.domain.repository.UserRepository;
import org.tipSell.eSecurity.domain.repositoryDTO.UserRepositoryDTO;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserServices {

	private final UserRepository userRepo;

	public UserRepositoryDTO loadUserByUsername(String username,String users) throws UsernameNotFoundException {
		UserRepositoryDTO user=null;
		
		if(users.equalsIgnoreCase(CLIENT_USER)) {
			user = userRepo.findUserUidByUser(username);
		}
		else if(users.equalsIgnoreCase(AUTHKEY_USER)) {
//			userId = userRepo.findUserUidByUserName(username);
		}
				//.orElseThrow(() -> new UsernameNotFoundException("Invalid Username and Password"));
		
		try {
			if (user != null) {
				return user;
			} 
			else {
				throw new UsernameNotFoundException("Invalid Username and Password");
			}
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException("Invalid Username and Password");
		}
	}
}
