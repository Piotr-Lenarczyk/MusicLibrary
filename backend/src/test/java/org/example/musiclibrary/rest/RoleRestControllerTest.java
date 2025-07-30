package org.example.musiclibrary.rest;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.example.musiclibrary.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoleRestControllerTest {
	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnValidJSON() {
		ResponseEntity<String> response = restTemplate.getForEntity("/roles/1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(1);

		String name = documentContext.read("$.name");
		assertThat(name).isEqualTo("ROLE_USER");
	}

	@Test
	void shouldGetRole() {
		ResponseEntity<Role> response = restTemplate.getForEntity("/roles/1", Role.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(1);

		assertThat(response.getBody().getName()).isEqualTo("ROLE_USER");
	}

	@Test
	void shouldNotReturnInvalidRole() {
		ResponseEntity<Role> response = restTemplate.getForEntity("/roles/999", Role.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

		assertThat(response.getBody()).isNull();
	}

	@Test
	void shouldCreateNewRole() {
		Role role = new Role(null, "ROLE_ADMIN");
		ResponseEntity<Void> response = restTemplate.postForEntity("/roles", role, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI location = response.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(location, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isNotNull();

		String name = documentContext.read("$.name");
		assertThat(name).isEqualTo("ROLE_ADMIN");
	}
}
