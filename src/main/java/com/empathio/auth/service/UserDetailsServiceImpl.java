package com.empathio.auth.service;

import com.empathio.auth.exception.NotFoundException;
import com.empathio.auth.exception.attributes.ExceptionFieldType;
import com.empathio.auth.mapper.GeneralMapper;
import com.empathio.auth.model.User;
import com.empathio.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GeneralMapper generalMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(User.class, username, ExceptionFieldType.NAME));

        return generalMapper.toUserDetails(user);
    }
}
