package org.tipSell.eSecurity.domain.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author aravinth
 * @since 2024
 *
 *        A sample source file for the code formatter preview
 */

//Master
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "oauth_realm")
public class OauthRealm extends AuditEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "realm_uid")
	private Long realmUid;

	@Column(name = "realm", unique = true)
	private String realm;

	public OauthRealm(Long realmUid) {
		super();
		this.realmUid = realmUid;
	}

	public OauthRealm(String realm) {
		super();
		this.realm = realm;
	}

	public OauthRealm(Long realmUid, Integer deleteFlag) {
		super();
		this.realmUid = realmUid;
		this.setDeleteFlag(deleteFlag);
	}

	
	
//	@JsonIgnore
//	@OneToMany(mappedBy = "realm", cascade = CascadeType.ALL)
//	private List<OauthClient> client;

}
