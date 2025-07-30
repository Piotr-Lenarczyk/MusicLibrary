package org.example.musiclibrary.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.musiclibrary.entity.Role;
import org.example.musiclibrary.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository roleRepository;

	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	public Optional<Role> findById(Long id) {
		return roleRepository.findById(id);
	}

	@Transactional
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Transactional
	public void deleteById(Long id) {
		roleRepository.deleteById(id);
	}
}
