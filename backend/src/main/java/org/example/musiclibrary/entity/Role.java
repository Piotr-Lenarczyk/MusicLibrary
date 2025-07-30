package org.example.musiclibrary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	public void setName(String name) {
		if (name.toUpperCase().startsWith("ROLE_")) {
			this.name = name.toUpperCase();
		} else {
			this.name = "ROLE_" + name.toUpperCase();
		}
	}
}
