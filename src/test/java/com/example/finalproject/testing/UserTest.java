package com.example.finalproject.testing;

import com.example.finalproject.dto.request.role.AddRoleRequest;
import com.example.finalproject.dto.request.user.AddUserRequest;
import com.example.finalproject.entity.Role;
import com.example.finalproject.entity.User;
import com.example.finalproject.mapper.UserRoleMapper;
import com.example.finalproject.repository.RoleRepository;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.service.UserRoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTest {
    @InjectMocks // mai intai foloseste UserRoleServiceImpl obiectul real si injecteaza-i toate dependintele mock-uite
    private UserRoleServiceImpl userRoleService;
    @Mock // nu folosim un userRepository real , folosim unul fake
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRoleMapper userRoleMapper;
    @Test
    public void addUser(){ // facem un test unitar pentru metoda addUser() din userRoleServiceImpl(obiect real), care are ca dependinta UserRepository si UserRoleMapper, RoleRepository, toate obiectele acestea fiind mock-uite
        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setUsername("andreea");
        addUserRequest.setPassword("1234");
        addUserRequest.setEmail("andreea1234@yahoo.com");
        List<String> roles = List.of("admin","client");
        addUserRequest.setRolesName(roles);

        User user = new User();
        user.setPassword("1234");
        user.setUsername("andreea");
        user.setEmail("andreea1234@yahoo.com");
        List<Role> roles1 = new ArrayList<>();
        for(String name : roles){
            AddRoleRequest addRoleRequest = new AddRoleRequest();
            addRoleRequest.setRoleName(name);
            Role role = new Role();
            role = userRoleMapper.fromAddRoleRequest(addRoleRequest);
            roles1.add(role);
        }
        user.setRoles(roles1);

        when(userRoleMapper.fromAddUserRequest(addUserRequest)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userRoleService.addUser(addUserRequest); // aici apelam metoda pe care vrem sa o testam din userRoleService obiect real

        verify(userRepository,times(1)).save(user); //pentru metode void


    }
}
