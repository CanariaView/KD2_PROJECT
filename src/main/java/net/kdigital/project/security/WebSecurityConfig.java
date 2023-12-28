package net.kdigital.project.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	
	@Autowired 
	// javax.sql.datasource
	private DataSource datasource;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	
		// 인증절차 없이 접근 가능한 Mapping 주소 설정
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/",
					"/signup",
					"/overview",
					"/products",
					"/insights",
					"/community",
					"/contact",
					"/faq",
					"/idCheck",
					"/requestData",
					"/note",
					"/selectReplyAll",
					"/insertReply",
					"/report2023/**",
					"/data/**",
					"/font/**",
					"/vendor/**",
					"/image/**",
					"/css/**",
					"/script/**").permitAll() // hasRole(): 역할에 맞는 사람만 들어올 수 있게 설정 가능
			.anyRequest().authenticated()     // 설정한 주소 이외에는 모두 로그인을 해야 함
			.and()
				.formLogin()					          // 사용자가 만든 폼을 이용해서 로그인을 하겠다
				.loginPage("/signin").permitAll() // 인증처리를 하는 URL 설정. 로그인 폼의 action까지 포함
				.usernameParameter("memberid")
				.passwordParameter("memberpwd")
			.and()
				.logout()
				.logoutUrl("/signout")
				.logoutSuccessUrl("/").permitAll()
			.and()
				.cors() // 위변조방지
			.and()
				.httpBasic();
		
		
		return http.build();

	}
	
	// 인증을 위한 쿼리
	@Autowired	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(datasource)
		// 인증(로그인)
		.usersByUsernameQuery(
				"SELECT memberid username, memberpwd password, enabled "
				+ "FROM p_member "
				+ "WHERE memberid = ?")
		// 권한
		.authoritiesByUsernameQuery(
				"SELECT memberid username, rolename role_name "
				+ "FROM p_member "
				+ "WHERE memberid = ?");
		
	}
	
	// 단방향 암호화: 역부호화 X
	/* Bean: spring framework에서 관리하는 메소드
	 * Bean 태그가 달려있는 메소드는 autowired를 통해 다른 곳에서 꺼내쓸 수 있음
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder(); 
	}

	
}

