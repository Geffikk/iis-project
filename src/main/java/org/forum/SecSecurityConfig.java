package org.forum;

import org.forum.entities.user.User;
import org.forum.service.UserDetailsServiceImpl;
import org.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Date;

import static org.forum.AccessRules.*;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private CsrfTokenRepository csrfTokenRepository;

    public SecSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        configureAccessRules(http);
        configureLoginForm(http);
        configureLogout(http);
        configureRememberMe(http);
        configureCsrf(http);
        configureEncodingFilter(http);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void configureAccessRules(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(FOR_AUTHORIZED_USERS).hasRole("USER")
                .antMatchers(FOR_ADMINS).hasAnyRole(ADMINS_ROLES)
                .antMatchers(FOR_EVERYONE).permitAll()
                .and().logout()
                .and().exceptionHandling().accessDeniedPage("/403");
    }

    private void configureLoginForm(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .permitAll();
    }

    private void configureLogout(HttpSecurity http) throws Exception {
        http.logout()
                .permitAll();
    }

    private void configureRememberMe(HttpSecurity http) throws Exception {
        http.rememberMe()
                .tokenValiditySeconds(2419200)
                .key("forum-key");
    }

    private void configureEncodingFilter(HttpSecurity http) {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        http.addFilterBefore(filter, CsrfFilter.class);
    }

    private void configureCsrf(HttpSecurity http) throws Exception {
        http.csrf()
                .csrfTokenRepository(csrfTokenRepository);
    }
}