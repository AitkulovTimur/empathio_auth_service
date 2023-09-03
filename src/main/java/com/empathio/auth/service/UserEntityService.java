package com.empathio.auth.service;

import com.empathio.auth.exception.AlreadyExistsException;
import com.empathio.auth.exception.NotFoundException;
import com.empathio.auth.exception.PasswordMismatchException;
import com.empathio.auth.exception.attributes.ExceptionFieldType;
import com.empathio.auth.mapper.GeneralMapper;
import com.empathio.auth.model.RoleType;
import com.empathio.auth.model.UserRegistrationRequest;
import com.empathio.auth.model.User;
import com.empathio.auth.model.UserDTO;
import com.empathio.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class UserEntityService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    GeneralMapper generalMapper;

    public ResponseEntity<UserDTO> registerUser(UserRegistrationRequest registrationRequest) {
        //TODO: add confirmation of email (functionality)
        final String username = registrationRequest.getUsername();
        final String email = registrationRequest.getEmail();
        throwIfExists(username, email);

        final String password = registrationRequest.getPassword();
        if (!password.equals(registrationRequest.getPasswordAgain()))
            throw new PasswordMismatchException();

        final boolean blocked = registrationRequest.isBlocked();

        User newUser = userRepository.save(User.builder()
                .username(username)
                .email(email)
                .password(password)
                .expirationDateTime(OffsetDateTime.now().plusDays(30))
                .roles(roleService.getRoleByTypeOrReturnNew(RoleType.ROLE_DEFAULT))
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .age(registrationRequest.getAge())
                .blocked(blocked).build());

        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/id").buildAndExpand(newUser.getId()).toUri())
                .body(generalMapper.toUserDto(newUser));
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(User.class, userId.toString(), ExceptionFieldType.ID));
    }

    public String getUserIdByUsername(String username) {
        return userRepository.returnIdByUsername(username)
                .orElseThrow(() -> new NotFoundException(User.class, username, ExceptionFieldType.USERNAME));
    }

    private void throwIfExists(String username, String email) {
        if (userRepository.existsByUsername(username))
            throw new AlreadyExistsException(User.class, username, ExceptionFieldType.USERNAME);

        if (userRepository.existsByEmail(email))
            throw new AlreadyExistsException(User.class, email, ExceptionFieldType.EMAIL);
    }
}
