package com.peace.users.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.peace.users.dao.UserDao;
import com.peace.users.model.PUserAuth;
import com.peace.users.model.UserAuthority;
import com.peace.users.model.mram.LnkRoleAuth;
import com.peace.users.model.mram.LutUsers;


@Component("userDetailsService")
@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;



	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException{
		LutUsers user =  userDao.findByUserName(username);
		if(user == null) {
            throw new UsernameNotFoundException("no user found with " + user);
        }
		List<GrantedAuthority> authorities = buildUserAuthority(user.getLnkOffRoles().get(0).getLutRole().getLnkRoleAuths());
		
		return buildUserForAuthentication(user, authorities);
		
		
	}

	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(com.peace.users.model.mram.LutUsers user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getUserpass(), user.getIsactive(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<LnkRoleAuth> set) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (LnkRoleAuth userRole : set) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getAuthName()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}
	
	public Object getHQLResult(String hqlString,String returnType){
		return userDao.getHQLResult(hqlString, returnType);
	}

}