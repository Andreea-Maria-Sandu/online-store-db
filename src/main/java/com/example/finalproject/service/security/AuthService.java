package com.example.finalproject.service.security;

import com.example.finalproject.dto.request.user.AddUserRequest;
import com.example.finalproject.dto.request.user.SignInRequest;
import com.example.finalproject.dto.request.user.UserRequest;
import com.example.finalproject.dto.response.singIn.SignInResponse;
import com.example.finalproject.entity.Role;
import com.example.finalproject.entity.User;
import com.example.finalproject.mapper.UserRoleMapper;
import com.example.finalproject.repository.RoleRepository;
import com.example.finalproject.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRoleMapper userRoleMapper;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, UserRoleMapper userRoleMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.userRoleMapper = userRoleMapper;
    }


    public User getUserByEmail(String email)
    {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);

        if(optionalUser.isPresent())
        {
            throw new RuntimeException("Email is already in use");
        }
        return null;
    }

    public void registerUser(AddUserRequest addUserRequest) {
        User user = userRoleMapper.fromAddUserRequest(addUserRequest);
            Optional<Role> optionalRole = roleRepository.findRoleByName(addUserRequest.getRolesName().get(0));
            if (optionalRole.isPresent()) {
                user.addRole(optionalRole.get());
            } else {
                throw new RuntimeException("Role with name " + addUserRequest.getUsername() + " is not in the db");
            }
            userRepository.save(user);
    }

    public SignInResponse signIn(SignInRequest signInRequest)
    {
        String username = signInRequest.getUsername();
        String password = signInRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return UserRoleMapper.fromUserDetailsImpl(userDetails);
    }

}
