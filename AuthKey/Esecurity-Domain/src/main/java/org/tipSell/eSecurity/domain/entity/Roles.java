package org.tipSell.eSecurity.domain.entity;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * aravinth 20-Jul-2024
 * 
 */

//This will be in Client

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "roles")
public class Roles extends AuditEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_uid")
	private Long roleUid;

	private String role;

	@Column(name = "default_role")
	private Boolean defaultRole;

	@ManyToOne
	@JoinColumn(name = "client_fk", referencedColumnName = "client_uid")
	@JsonIgnore
	private OauthClient client;

	public Roles(String role, OauthClient client) {
		super();
		this.role = role;
		this.client = client;
	}

	public Roles(Long roleUid) {
		super();
		this.roleUid = roleUid;
	}

}
