package io.spring.blog.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/roles/**").hasRole("ADMIN")
			.antMatchers("/api/auth/**").permitAll()
			.antMatchers(HttpMethod.GET, "/api/**").permitAll()
			.antMatchers(HttpMethod.POST, "/api/**").hasAnyRole("ADMIN", "USER")
			.antMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("ADMIN", "USER")
			.antMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("ADMIN", "USER")
//		.antMatchers(HttpMethod.POST, "/api/roles/**").hasAuthority(ROLE_WRITE.getPermission())
//		.antMatchers(HttpMethod.PUT, "/api/roles/**").hasAuthority(ROLE_WRITE.getPermission())
//		.antMatchers(HttpMethod.DELETE, "/api/roles/**").hasAuthority(ROLE_WRITE.getPermission())
			.anyRequest().authenticated().and().httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		UserDetails adminUser = User.builder()
//				.username("admin")
//				.password(passwordEncoder.encode("password"))
//				.roles(ADMIN.name())
//				.authorities(ADMIN.getGrantedAuthorities())
//				.build();
//
//		UserDetails editorUser = User.builder()
//				.username("editor")
//				.password(passwordEncoder.encode("password"))
//				.roles(EDITOR.name())
//				.authorities(EDITOR.getGrantedAuthorities())
//				.build();
//
//		return new InMemoryUserDetailsManager(adminUser, editorUser);
//	}

}
