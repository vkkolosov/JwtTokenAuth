package vk.kolosov.insidejwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vk.kolosov.insidejwt.model.UserInfo;
import vk.kolosov.insidejwt.security.jwt.JwtUserInfo;
import vk.kolosov.insidejwt.security.jwt.JwtUserInfoFactory;
import vk.kolosov.insidejwt.service.LocalService;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    private final LocalService localService;

    @Autowired
    public JwtUserDetailsService(LocalService localService) {
        this.localService = localService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = localService.findUserInfoByName(username);

        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found");
        }

        JwtUserInfo jwtUserInfo = JwtUserInfoFactory.create(userInfo);

        return jwtUserInfo;
    }
}
