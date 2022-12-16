package com.ajou.noop.user;

import com.ajou.noop.domain.User;
import com.ajou.noop.user.dto.LoginDto;
import com.ajou.noop.user.dto.SignupDto;
import com.ajou.noop.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    ResultActions trySignup(String email, String password, String username, String desc) throws Exception {
        SignupDto signupDto = new SignupDto(email, password, username, desc);
        String content = objectMapper.writeValueAsString(signupDto);
        return mockMvc.perform(post("/users/signup")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("회원 가입")
    void signUpSubmit() throws Exception {
        trySignup("email@email.com", "password", "username","description~~")
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
        User user = userRepository.findByEmail("email@email.com").orElse(new User());
        assertNotEquals(user.getPassword(), passwordEncoder.encode("password"));
    }

    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        trySignup("email@email.com", "password", "username","description~~")
                .andExpect(status().is2xxSuccessful());
        LoginDto loginDto = new LoginDto("email@email.com", "password");
        String content = objectMapper.writeValueAsString(loginDto);
        mockMvc.perform(post("/users/login")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}