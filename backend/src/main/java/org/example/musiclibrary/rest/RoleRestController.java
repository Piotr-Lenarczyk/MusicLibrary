package org.example.musiclibrary.rest;

import lombok.RequiredArgsConstructor;
import org.example.musiclibrary.entity.Role;
import org.example.musiclibrary.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleRestController {
	private final RoleService roleService;

	@GetMapping
	public ResponseEntity<List<Role>> getAllRoles() {
		return ResponseEntity.ok(roleService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
		Optional<Role> role = roleService.findById(id);
		if (role.isPresent()) {
			return ResponseEntity.ok(role.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<Void> createRole(@RequestBody Role role, UriComponentsBuilder ecb) {
		Role newRole = roleService.save(role);
		URI location = ecb.path("/roles/{id}").buildAndExpand(newRole.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role newRole) {
		if (roleService.findById(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			newRole.setId(id);
			roleService.save(newRole);
		}
		return ResponseEntity.ok(newRole);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Role> deleteRole (@PathVariable Long id) {
		if (roleService.findById(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		roleService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
