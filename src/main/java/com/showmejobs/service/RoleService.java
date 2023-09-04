package com.showmejobs.service;

import com.showmejobs.domain.Role;
import com.showmejobs.domain.enums.RoleType;
import com.showmejobs.exception.ResourceNotFoundException;
import com.showmejobs.exception.message.ErrorMessage;
import com.showmejobs.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public Role findByType(RoleType roleType) {
        return roleRepository.findByType(roleType).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, roleType.name()), HttpStatus.NOT_FOUND));
    }

    public void createRoles() {
        List<Role> roles = new ArrayList<>();
        Role roleSupplier = new Role();
        Role roleRetailer = new Role();
        roleSupplier.setType(RoleType.ROLE_USER);
        roleRetailer.setType(RoleType.ROLE_ADMIN);
        roles.add(roleSupplier);
        roles.add(roleRetailer);
        roleRepository.saveAll(roles);
    }
}
