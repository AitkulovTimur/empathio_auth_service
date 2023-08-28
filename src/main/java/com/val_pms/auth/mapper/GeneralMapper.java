package com.val_pms.auth.mapper;

import com.val_pms.auth.model.Role;
import com.val_pms.auth.model.User;
import com.val_pms.auth.model.UserDTO;
import com.val_pms.auth.model.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface GeneralMapper {
    GeneralMapper INSTANCE = Mappers.getMapper(GeneralMapper.class);

    @Mapping(target = "authorities", source = "roles", qualifiedByName = "convertRolesToAuthorities")
    UserDetailsImpl toUserDetails(User user);

    @Named("convertRolesToAuthorities")
    default List<GrantedAuthority> convertRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleType().roleName))
                .collect(Collectors.toList());
    }

    @Mapping(target = "roles", source = "authorities", qualifiedByName = "convertAuthoritiesToRoles")
    UserDTO toUserDto(UserDetailsImpl udi);

    @Named("convertAuthoritiesToRoles")
    default Set<String> convertAuthoritiesToRoles(Collection<? extends GrantedAuthority>  authorities) {
        return authorities.stream().
                map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "roles", source = "roles", qualifiedByName = "convertRolesToStrings")
    UserDTO toUserDto(User user);


    @Named("convertRolesToStrings")
    default Set<String> convertRolesToStrings(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getRoleType().roleName)
                .collect(Collectors.toSet());
    }
}
