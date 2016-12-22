package com.malinowski.blog.services.implementations;

import com.malinowski.blog.model.Authorities;
import com.malinowski.blog.model.User;
import com.malinowski.blog.repositories.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jakub on 04.12.2016.
 */
@Service
public class UserDeatailsServiceImpl implements UserDetailsService{

    private UserRepository userRepository;

    @Autowired
    public UserDeatailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) throw new UsernameNotFoundException(username + " not found");

        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());

        return buildUserForAuthentication(user, authorities);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(
            User user, List<GrantedAuthority> grantedAuthorities){
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), user.isEnabled(),
                true, true, true, grantedAuthorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<Authorities> userRoles){
        Set<GrantedAuthority> setAuths = new HashSet<>();

        for (Authorities auth : userRoles){
            setAuths.add(new SimpleGrantedAuthority(auth.getAuthority()));
        }

        List<GrantedAuthority> result = new ArrayList<>(setAuths);

        return result;
    }
}
