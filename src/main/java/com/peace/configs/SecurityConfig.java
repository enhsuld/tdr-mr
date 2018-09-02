package com.peace.configs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.util.WebUtils;


//import com.allanditzel.springframework.security.web.csrf.CsrfTokenResponseHeaderBindingFilter;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class);

	@Autowired
    private UserDetailsService userDetailService;
        
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception { 
        auth.inMemoryAuthentication().withUser("admin").password("mram1234").roles("SUPER");
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
     
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and()
		.authorizeRequests()
				.antMatchers("/bower_components/**").permitAll()
				.antMatchers("/file_manager/**").permitAll()
				.antMatchers("/gulp-tasks/**").permitAll()				
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/data/**").permitAll()	
				.antMatchers("/kendoui/**").permitAll()	
				.antMatchers("/package.json").permitAll()	
				.antMatchers("/font.css").permitAll()
				.antMatchers("/app/**").permitAll()	
				.antMatchers("/api/**").permitAll()	
				.antMatchers("/user").permitAll()	
				.antMatchers("/front/**").permitAll()	
				.antMatchers("/admin/**").permitAll()	
				.antMatchers("/service/send-mail ").permitAll()	
				.antMatchers("user").permitAll()	
				.antMatchers("/index.html", "/user","/","/login","/public/**","/search/**","/division/**","/v2/**").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()			
					.loginPage("/login").permitAll()
					.defaultSuccessUrl("/admin")
				.failureUrl("/login?error")
				.and().rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(86400)
				.and()
					.logout()     
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/login?logout")        
					.deleteCookies("remember-me")
					.deleteCookies("JSESSIONID") 			
	            .and()
					.csrf().disable();
	}

	@Autowired
	private DataSource dataSource;
	 
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		return tokenRepository;
	}


}