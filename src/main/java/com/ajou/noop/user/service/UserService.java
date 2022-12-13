package com.ajou.noop.user.service;

import com.ajou.noop.domain.User;
import com.ajou.noop.user.dto.LoginDto;
import com.ajou.noop.user.dto.SignupDto;
import com.ajou.noop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Transactional
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Transactional
    public void signup(SignupDto signupDto) {
        // 중복 정보 처리 예정 ?
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        userRepository.save(signupDto.toEntity());
    }

    public String login(LoginDto loginDto) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        User user = (User) authentication.getPrincipal();

        Instant now = Instant.now();
        long expiry = 3600L;

        String scope =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(joining(" "));

        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .issuer("noop")
                        .issuedAt(now)
                        .expiresAt(now.plusSeconds(expiry))
                        .subject(format("%s,%s", user.getEmail(), user.getUsername()))
                        .claim("roles", scope)
                        .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
