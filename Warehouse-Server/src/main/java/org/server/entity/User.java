package org.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class User implements UserDetails {

	private int id;
	private String username;
	private String password;
	private String phone;
	private boolean enabled;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC+8")
	private Date registerTime;
	private List<Role> roles;

	public User() {}

	public User(int id, String username, String password, String phone, boolean enabled, Date registerTime, List<Role> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.enabled = enabled;
		this.registerTime = registerTime;
		this.roles = roles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return Objects.equals(username, user.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	@JsonIgnore
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
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
