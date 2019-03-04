package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MailSender mailSender;

    @Autowired
    public UserService(final UserRepository userRepository, final MailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user) {
        final User username = userRepository.findByUsername(user.getUsername());

        if (username != null) {
            return false;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String link = "http://192.168.100.118:8080/activate/";
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. \n" +
                            "To complete registration you need to click this link: \n" +
                            link + "%s", user.getUsername(), user.getActivationCode());

            mailSender.send(user.getEmail(), "Activation code", message);
        }

        return true;
    }

    public boolean activateUser(final String code) {
        final User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);
        return true;
    }
}
