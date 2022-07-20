package com.example.springmyitems.service;

import com.example.springmyitems.entity.Role;
import com.example.springmyitems.entity.User;
import com.example.springmyitems.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }


    public void create(User user) {
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findById(int id) {
        return userRepository.getById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }
}
