package net.kdigital.project.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

// VO에 security 기능을 구현할 수 있는 interface 상속
public class Member implements UserDetails{
	private String memberid;
	private String memberpwd;
	private String membername;
	private String email;
	private String phone;
	private String address;
	private int imageseq;
	private boolean enabled;	// 계정 상태 정보 0: 사용 불가능, 1: 사용 가능
	private String rolename;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPassword() {
		return this.memberpwd;
	}
	@Override
	public String getUsername() {
		return this.memberid;
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

}