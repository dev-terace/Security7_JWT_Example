package com.example.demo.domain.user.service;

import com.example.demo.domain.user.dto.UserRequestDTO;
import com.example.demo.domain.user.entity.UserEntity;
import com.example.demo.domain.user.entity.UserRole;
import com.example.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public void join(UserRequestDTO dto)
    {
        String userName = dto.userName();
        String password = dto.password();

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setRole(UserRole.USER);

        userRepository.save(userEntity);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity entity = userRepository.findByUserName(username).orElseThrow();

        return User.builder()
                .username(entity.getUserName())
                .password(entity.getPassword())
                .roles(entity.getRole().name())
                .build();

    }
}
