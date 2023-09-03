package com.empathio.auth.service;

import com.empathio.auth.model.Role;
import com.empathio.auth.model.RoleType;
import com.empathio.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Set<Role> getRoleByTypeOrReturnNew(RoleType... roleType) {
        return Arrays.stream(roleType)
                .map(rt -> roleRepository.findByRoleType(rt).orElse(new Role(rt)))
                .collect(Collectors.toSet());
    }
}
