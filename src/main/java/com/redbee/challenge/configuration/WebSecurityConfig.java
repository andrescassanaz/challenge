package com.redbee.challenge.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

/**
 * @author Andres Cassanaz
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userService;
	
		
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.addFilterBefore(corsFilter(), SessionManagementFilter.class)
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
				.csrf().disable()
				.authenticationProvider(authenticationProvider())
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				.antMatchers(HttpMethod.GET,"/socket/**").permitAll()
				.antMatchers(HttpMethod.POST,"/socket/**").permitAll()
				.anyRequest().authenticated()				
				.and().logout().clearAuthentication(true).invalidateHttpSession(true)
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
