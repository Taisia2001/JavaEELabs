package org.shutiak.lab8.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.shutiak.lab8.repository.RoleRepository;
import org.shutiak.lab8.repository.UserRepository;
import org.shutiak.lab8.service.JwtTokenGenerator;
import org.shutiak.lab8.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.POST;

@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(customLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthenticationFilter(userDetailsService(), jwtTokenGenerator), CustomLoginFilter.class);
    }
    @Override


    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new UserService(userRepository,roleRepository, jwtTokenGenerator, passwordEncoder());
    }

    @Bean
    @SneakyThrows
    @Override
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    private CustomLoginFilter customLoginFilter() {
        final CustomLoginFilter customLoginFilter =  new CustomLoginFilter(authenticationManagerBean(), objectMapper, jwtTokenGenerator);
        customLoginFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", POST.name()));

        return customLoginFilter;
    }
}
