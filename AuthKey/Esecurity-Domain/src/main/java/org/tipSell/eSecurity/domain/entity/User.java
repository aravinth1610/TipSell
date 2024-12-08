package org.tipSell.eSecurity.domain.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.ReadOnlyProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

//Master
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "user")
public class User extends AuditEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_uid")
	private Long userUid;

	@Column(name = "user_name")
	private String userName;

	private String mail;

	@JsonIgnore
	@Column(name = "authemtication_code")
	private String authenticationCode;

	@JsonIgnore
	private String password;

	@ReadOnlyProperty
	@Column(name = "user_key")
	private String userKey;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<UserRole> roles;

	@JsonIgnore
	@ManyToOne
	// (cascade = CascadeType.ALL)
	@JoinColumn(name = "client_fk", referencedColumnName = "client_uid")
	private OauthClient client;

	// This method sets roles and associates each role with the user
	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
		if (roles != null && !roles.isEmpty()) {
			for (UserRole role : roles) {
				role.setUser(this); // Setting the user reference for each role
			}
		}
	}

}
