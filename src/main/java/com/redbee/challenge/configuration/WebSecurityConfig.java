package com.redbee.challenge.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userService;


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.addFilterBefore(corsFilter(), SessionManagementFilter.class)
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class).csrf().disable()
				.authenticationProvider(authenticationProvider()).authorizeRequests().antMatchers(HttpMethod.OPTIONS)
				.permitAll().antMatchers(HttpMethod.PUT, "/boards").permitAll()
				.antMatchers(HttpMethod.DELETE, "/boards/{boardId}").permitAll()
				.antMatchers(HttpMethod.GET, "/boards/{boardId}").permitAll().antMatchers("/api/**").authenticated()
				.antMatchers("/").permitAll().and().logout().clearAuthentication(true).invalidateHttpSession(true)
				.permitAll();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CorsFilter corsFilter() {
		CorsFilter filter = new CorsFilter();
		return filter;
	}

	@Bean
	JwtFilter jwtFilter() {
		JwtFilter filter = new JwtFilter();
		return filter;
	}
	
	 @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	   @Override
	   public AuthenticationManager authenticationManagerBean() throws Exception {
	       return super.authenticationManagerBean();
	   }

}
