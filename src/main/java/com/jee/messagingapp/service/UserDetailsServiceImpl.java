package com.jee.messagingapp.service;

import com.jee.messagingapp.domain.Role;
import com.jee.messagingapp.domain.User;
import com.jee.messagingapp.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired UserRepository userRepository;

    @Override
    @Transactional()
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getName()));


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);

    }
}
