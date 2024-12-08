package org.tipSell.eSecurity.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.slf4j.MDC;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author aravinth
 * @since 2024
 *
 *        A sample source file for the code formatter preview
 */
@Getter
@Setter
@MappedSuperclass
public class AuditEntity {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	@Column(updatable = false)
	private Long createdBy;

	private Long updatedBy;

	private Integer deleteFlag;

	private String clientIpAddress;

	private String localIpAddress;

	@PrePersist
	protected void onCreate() {
		this.createdOn = new Date();
		this.deleteFlag = 0;
		this.createdBy = Long.valueOf(MDC.get("user")); // Fetch the current user
		this.clientIpAddress = MDC.get("clientIpAddress"); // Get the current IP address
		this.localIpAddress = MDC.get("localIpAddress");
	}

//	    private String getCurrentUser() {
//		        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		        return auth != null ? auth.getName() : "anonymous";
//		    }

	@PreUpdate
	protected void onUpdate() {
		this.updatedOn = new Date();
//		this.deleteFlag = 0;
		this.updatedBy = Long.valueOf(MDC.get("user")); // Update the current user
		this.clientIpAddress = MDC.get("clientIpAddress");
		this.localIpAddress = MDC.get("localIpAddress");
	}

}