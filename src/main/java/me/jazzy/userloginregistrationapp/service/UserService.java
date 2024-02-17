package me.jazzy.userloginregistrationapp.service;

import lombok.AllArgsConstructor;
import me.jazzy.userloginregistrationapp.model.RegisterRequest;
import me.jazzy.userloginregistrationapp.model.User;
import me.jazzy.userloginregistrationapp.model.UserRole;
import me.jazzy.userloginregistrationapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with " + email + "not found"));
    }

    public String saveUser(User user) {

        boolean userExists = userRepository.findByEmail(user.getEmail())
                .isPresent();

        if(userExists)
            throw new IllegalStateException("Email already taken");

        userRepository.save(user);

        return "its working";
    }


}
