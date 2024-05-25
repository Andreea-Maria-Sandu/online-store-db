package com.example.finalproject.testing;

import com.example.finalproject.dto.request.role.AddRoleRequest;
import com.example.finalproject.entity.Role;
import com.example.finalproject.mapper.UserRoleMapper;
import com.example.finalproject.repository.RoleRepository;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.service.UserRoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleTest {
    @InjectMocks
    private UserRoleServiceImpl userRoleService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleMapper userRoleMapper;
    @Test
    public void addRole(){
        AddRoleRequest addRoleRequest = new AddRoleRequest();
        addRoleRequest.setRoleName("admin");

        Role role = new Role();
        role.setName("admin");

        when(userRoleMapper.fromAddRoleRequest(addRoleRequest)).thenReturn(role);
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        userRoleService.addRole(addRoleRequest);

        verify(roleRepository,times(1)).save(role);
    }
}
