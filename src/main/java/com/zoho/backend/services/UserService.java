package com.zoho.backend.services;
import com.zoho.backend.classes.user.LoginInput;
import com.zoho.backend.exceptions.UserException;
import com.zoho.backend.model.User;
import com.zoho.backend.provider.JwtProvider;
import com.zoho.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@Service("userService")
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;



    @Transactional
    public ResponseEntity signup(User user) {
            Boolean existingUser = userRepository.existsByEmailEquals(user.getEmail());
            if (!existingUser) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                String token = jwtProvider.createToken(user.getUserId());
                Map<String, String> response = new HashMap<>();
                response.put("user_id", user.getUserId().toString());
                response.put("token", token);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with same email already registered");
            }
    }

    @Transactional
    public ResponseEntity login(LoginInput user) {

        try {
            User existingUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() ->  new UserException("Email / password is incorrect"));
            boolean passwordMatches = new BCryptPasswordEncoder().matches(user.getPassword(), existingUser.getPassword());

            if(passwordMatches) {
                String token = jwtProvider.createToken(existingUser.getUserId());
                Map<String, String> model = new HashMap<>();
                model.put("user_id", existingUser.getUserId().toString());
                model.put("token", token);
                return ok(model);
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email / password is incorrect");
            }
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email / password is incorrect");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {


        User user = userRepository.findByUserIdEquals(Long.parseLong(userId)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUserId().toString(), user.getPassword(), new ArrayList<>());

    }
}
