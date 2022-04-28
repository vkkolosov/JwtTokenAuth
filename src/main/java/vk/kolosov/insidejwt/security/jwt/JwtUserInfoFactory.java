package vk.kolosov.insidejwt.security.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import vk.kolosov.insidejwt.model.UserInfo;

import java.util.Collections;

public final class JwtUserInfoFactory {

    public static JwtUserInfo create(UserInfo userInfo) {
        return new JwtUserInfo(
                userInfo.getId(),
                userInfo.getName(),
                userInfo.getPassword(),
                true,
                Collections.singleton(new SimpleGrantedAuthority("USER"))
        );
    };

}
