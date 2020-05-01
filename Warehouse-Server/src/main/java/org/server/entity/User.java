package org.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements UserDetails {

	private int id;
	private String username;
	private String password;
	private String phone;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date registerTime;
	private List<Role> roles;
	private boolean enabled;

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getPhone() {
		return phone;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	@Override
	@JsonIgnore
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", phone='" + phone + '\'' +
				", enabled=" + enabled +
				", registerTime=" + registerTime +
				", roles=" + roles +
				'}';
	}
}
