package com.example.bnyan.Service;

import com.example.bnyan.Api.ApiException;
import com.example.bnyan.DTO.CustomerDTO;
import com.example.bnyan.Model.Customer;
import com.example.bnyan.Model.User;
import com.example.bnyan.Repository.CustomerRepository;
import com.example.bnyan.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public List<User> get() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) throw new ApiException("No users found");
        return users;
    }

    public void registerCustomer(CustomerDTO customerDTO) {
        if (userRepository.getUserByUsername(customerDTO.getUsername()) != null)
            throw new ApiException("Username already exists");

        if (userRepository.getUserByEmail(customerDTO.getEmail()) != null)
            throw new ApiException("Email already exists");

        User user = new User();
        user.setUsername(customerDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hash);
        user.setEmail(customerDTO.getEmail());
        user.setPhoneNumber(customerDTO.getPhoneNumber());
        user.setFullName(customerDTO.getFullName());
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        Customer customer = new Customer();
        customer.setUser(user);
        customerRepository.save(customer);
    }

    public void update(Integer sessionUserId, User user) {

        User sessionUser = userRepository.getUserById(sessionUserId);
        if (sessionUser == null) throw new ApiException("User not found");

        User targetUser = sessionUser;

        if (sessionUser.getRole().equals("ADMIN") && user.getId() != null) {
            targetUser = userRepository.getUserById(user.getId());
            if (targetUser == null) throw new ApiException("Target user not found");
        }

        if (!targetUser.getUsername().equals(user.getUsername()) &&
                userRepository.getUserByUsername(user.getUsername()) != null)
            throw new ApiException("Username already exists");

        if (!targetUser.getEmail().equals(user.getEmail()) &&
                userRepository.getUserByEmail(user.getEmail()) != null)
            throw new ApiException("Email already exists");

        targetUser.setUsername(user.getUsername());
        targetUser.setEmail(user.getEmail());
        targetUser.setPhoneNumber(user.getPhoneNumber());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            targetUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }

        userRepository.save(targetUser);
    }


    public void delete(Integer userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) throw new ApiException("User not found");

        userRepository.delete(user);
    }

    public User getUserById(Integer id) {
        User user = userRepository.getUserById(id);
        if (user == null) throw new ApiException("User not found");
        return user;
    }

    public User getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) throw new ApiException("User with username " + username + " not found");
        return user;
    }

    public List<User> getUsersByRole(String role) {
        List<User> users = userRepository.getUsersByRole(role);
        if (users.isEmpty()) throw new ApiException("No users with role " + role + " found");
        return users;
    }
}