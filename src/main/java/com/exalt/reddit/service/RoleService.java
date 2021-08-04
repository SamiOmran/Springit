package com.exalt.reddit.service;

import com.exalt.reddit.model.Role;
import com.exalt.reddit.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
      return roleRepository.findByName(name);
    }
}
