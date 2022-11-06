package com.soteria.services;

import com.soteria.exceptions.role.RoleAlreadyExists;
import com.soteria.exceptions.role.RoleNotFound;
import com.soteria.models.Role;
import com.soteria.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role getRole(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFound(String.format("Role with ID: %s not found", id)));
    }

    public Role getRole(String name) {
        return roleRepository.findRoleByName(name).orElseThrow(() -> new RoleNotFound(String.format("Role with name: %s not found", name)));
    }

    public Role addRole(Role role) {
        Optional<Role> checkRoleByName = roleRepository.findRoleByName(role.getName());

        if(checkRoleByName.isPresent()) {
            throw new RoleAlreadyExists(String.format("Role '%s' already exists", role.getName()));
        }

        return roleRepository.save(role);
    }

    public Role updateRole(Long id, String name) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFound(String.format("Role with ID: %s not found", id)));
        Optional<Role> checkRoleByName = roleRepository.findRoleByName(name);

        if(checkRoleByName.isPresent()) {
            throw new RoleAlreadyExists(String.format("Role '%s' already exists", name));
        } else {
            role.setName(name);
        }

        return role;
    }

    public void deleteRole(Long id) {
        roleRepository.findById(id).orElseThrow(() -> new RoleNotFound(String.format("Role with ID: %s not found", id)));
        roleRepository.deleteById(id);
    }
}
