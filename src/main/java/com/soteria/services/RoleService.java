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

/**
 *  Role service
 *  @author  Ramphy Aquino Nova
 *  @version 2022.11.15
 */
@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Get all roles
     * @return List<Role>
     */
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    /**
     * Get single role by id
     * @param id Role id
     * @return Role
     */
    public Role getRole(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFound(String.format("Role with ID: %s not found", id)));
    }

    /**
     * Get role by name
     * @param name Role name
     * @return Role
     */
    public Role getRole(String name) {
        return roleRepository.findRoleByName(name).orElseThrow(() -> new RoleNotFound(String.format("Role with name: %s not found", name)));
    }

    /**
     * Add role
     * @param role New role object
     * @return Role
     */
    public Role addRole(Role role) {
        Optional<Role> checkRoleByName = roleRepository.findRoleByName(role.getName());

        if(checkRoleByName.isPresent()) {
            throw new RoleAlreadyExists(String.format("Role '%s' already exists", role.getName()));
        }

        return roleRepository.save(role);
    }

    /**
     * Update role name
     * @param id Role id
     * @param name New role name
     * @return Role
     */
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

    /**
     * Delete role by given id
     * @param id Role id
     */
    public void deleteRole(Long id) {
        roleRepository.findById(id).orElseThrow(() -> new RoleNotFound(String.format("Role with ID: %s not found", id)));
        roleRepository.deleteById(id);
    }
}
