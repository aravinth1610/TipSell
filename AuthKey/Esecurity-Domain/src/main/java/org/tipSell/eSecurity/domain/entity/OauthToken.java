package org.tipSell.eSecurity.domain.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.tipSell.domain.enums.Unit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

//Client

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "oauth_token")
public class OauthToken extends AuditEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_uid")
	private Long tokenUid;

	@Column(name = "access_token_expiration")
	private long accessTokenExpiration;

	@Column(name = "access_token_unit")
	@Enumerated(value = EnumType.STRING)
	private Unit accessTokenUnit;

	@Column(name = "refresh_token_expiration")
	private long refreshTokenExpiration;

	@Column(name = "refresh_token_unit")
	@Enumerated(value = EnumType.STRING)
	private Unit refreshTokenUnit;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "client_fk", referencedColumnName = "client_uid")
	private OauthClient client;

	public OauthToken(long accessTokenExpiration, Unit accessTokenUnit, long refreshTokenExpiration,
			Unit refreshTokenUnit) {
		super();
		this.accessTokenExpiration = accessTokenExpiration;
		this.accessTokenUnit = accessTokenUnit;
		this.refreshTokenExpiration = refreshTokenExpiration;
		this.refreshTokenUnit = refreshTokenUnit;
	}

	public OauthToken(Long tokenUid, long accessTokenExpiration, Unit accessTokenUnit, long refreshTokenExpiration,
			Unit refreshTokenUnit) {
		super();
		this.tokenUid = tokenUid;
		this.accessTokenExpiration = accessTokenExpiration;
		this.accessTokenUnit = accessTokenUnit;
		this.refreshTokenExpiration = refreshTokenExpiration;
		this.refreshTokenUnit = refreshTokenUnit;
	}


	

}
