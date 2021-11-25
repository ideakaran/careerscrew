package com.project.careerscrew.security;

import com.project.careerscrew.entities.UserEntity;
import com.project.careerscrew.repository.UserRepository;
import com.project.careerscrew.utils.exceptions.AppExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(AppExceptionConstants.BAD_LOGIN_CREDENTIALS));
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRole role : userEntity.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getUserRole()));
        }
        return new CustomUserDetails(userEntity.getId(), userEntity.getEmail(), userEntity.getPassword(), grantedAuthorities);
    }
}
