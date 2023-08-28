package com.val_pms.auth.service;

import com.val_pms.auth.exception.NotFoundException;
import com.val_pms.auth.exception.attributes.ExceptionFieldType;
import com.val_pms.auth.model.RoleType;
import com.val_pms.auth.model.Role;
import com.val_pms.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role getRoleByType(RoleType roleType) {
        String roleTypeString = roleType.name();
        return roleRepository.findByRoleType(roleTypeString).orElseThrow(() -> new NotFoundException(Role.class, roleTypeString, ExceptionFieldType.NAME));
    }
}
