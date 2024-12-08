package org.tipSell.eSecurity.domain.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.tipSell.domain.enums.GrantTypes;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

//Master
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "oauth_client")
public class OauthClient extends AuditEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_uid")
	private Long clientUid;
	
	@Column(name = "client_id",unique = true)
	private String clientID;
	
	@Column(name = "redirect_uri")
	private String redirectURI;
	
	@Column(name = "grant_type")
	@Enumerated(value = EnumType.STRING)
	private GrantTypes grantType;
	
	@Column(name = "client_secret", updatable = false, unique = true)
	private String clientSecret;
	
	@Column(name = "verify_mail")
	private Integer verifyMail;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "relam_fk", referencedColumnName = "realm_uid")
	private OauthRealm realm;

	@JsonIgnore
	@OneToOne(mappedBy = "client")
	private OauthToken token;
	
	@JsonIgnore
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	private Set<Attribute> attribute;

//	@JsonIgnore
//	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
//	private List<Customers> customer;

   // This method sets roles and associates each role with the user
    public void setToken(OauthToken token) {
        this.token = token;
        if (token != null) {
        	token.setClient(this); // Setting the user reference for each role
        }
    }

	public OauthClient(String clientID, GrantTypes grantType, String clientSecret, OauthRealm realm, OauthToken token) {
		super();
		this.clientID = clientID;
		this.grantType = grantType;
		this.clientSecret = clientSecret;
		this.realm = realm;
		this.setToken(token);
	}
	
	public OauthClient(String clientID, GrantTypes grantType, String clientSecret, OauthRealm realm) {
		super();
		this.clientID = clientID;
		this.grantType = grantType;
		this.clientSecret = clientSecret;
		this.realm = realm;
	}	

	public OauthClient(Long clientUid) {
		this.clientUid = clientUid;
	}
	
	

	
}
