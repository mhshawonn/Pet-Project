package com.example.demo.Models;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	@Column(name = "verification_code")
	private String verificationCode;
	
	@Column(name = "verification_expiration")
	private LocalDateTime verificationCodeExpiresAt;
	
	
	public User( String name, String email, String encodedPassword)  {
		this.name = name;
		this.email = email;
		this.password = encodedPassword;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority > getAuthorities() {
		return List.of();
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
