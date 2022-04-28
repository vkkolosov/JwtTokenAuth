package vk.kolosov.insidejwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vk.kolosov.insidejwt.dto.AuthenticationRequestDto;
import vk.kolosov.insidejwt.model.UserInfo;
import vk.kolosov.insidejwt.security.jwt.JwtAuthenticationException;
import vk.kolosov.insidejwt.security.jwt.JwtTokenProvider;
import vk.kolosov.insidejwt.service.LocalService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final LocalService localService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, LocalService localService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.localService = localService;
    }
    @GetMapping("/signup")

    public ResponseEntity signup(@RequestBody AuthenticationRequestDto authenticationRequestDto) {

        UserInfo userInfo = new UserInfo();
        userInfo.setName(authenticationRequestDto.getUsername());
        userInfo.setPassword(authenticationRequestDto.getPassword());

        try {
            localService.register(userInfo);
        } catch (JwtAuthenticationException e){
            return ResponseEntity.ok("User already exist");
        }
        String token = jwtTokenProvider.createToken(userInfo.getName());

        Map<Object, Object> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {

        try {
            String username = authenticationRequestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequestDto.getPassword()));
            UserInfo userInfo = localService.findUserInfoByName(username);

            if (userInfo == null) {
                return ResponseEntity.ok("User not found, please register on /signup");
            }

            String token = jwtTokenProvider.createToken(username);

            Map<Object, Object> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

    }
}
