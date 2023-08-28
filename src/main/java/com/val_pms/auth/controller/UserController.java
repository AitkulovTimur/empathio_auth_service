package com.val_pms.auth.controller;

import com.val_pms.auth.mapper.GeneralMapper;
import com.val_pms.auth.model.UserDTO;
import com.val_pms.auth.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/api/user")
public class UserController {
    @Autowired
    UserEntityService userService;

    @Autowired
    GeneralMapper generalMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return new ResponseEntity<>(
                generalMapper.toUserDto(userService.getUserById(id)),
                HttpStatus.OK);
    }
}
