package com.immortaltrial.game.security;

import com.immortaltrial.game.security.services.UserDetailsServiceImpl;
import jakarta.servlet.DispatcherType;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired private DataSource dataSource;

    @Value("${immortaltrial.app.secret}")
    private String secret;

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    @Bean
    //    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //        http.csrf(AbstractHttpConfigurer::disable)
    //                .exceptionHandling(exception ->
    // exception.authenticationEntryPoint(unauthorizedHandler()))
    //                .sessionManagement(session ->
    // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //                .authorizeHttpRequests(auth ->
    //                        auth.requestMatchers("/api/auth/**").permitAll()
    //                                .requestMatchers("/api/test/**").permitAll()
    //                                .anyRequest().authenticated());
    //
    //        http.authenticationProvider(authenticationProvider());
    //
    //        http.addFilterBefore(authenticationJwtTokenFilter(),
    // UsernamePasswordAuthenticationFilter.class);
    //
    //        return http.build();
    //    }

    //    @Bean
    //    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //        http.
    //
    //    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        auth ->
                                auth.dispatcherTypeMatchers(
                                                DispatcherType.FORWARD, DispatcherType.ERROR)
                                        .permitAll()
                                        .requestMatchers(
                                                "/static/**",
                                                "/registration",
                                                "/registration/**",
                                                "/login",
                                                "/main.css",
                                                "/htmx.min.js")
                                        .permitAll()
                                        .requestMatchers("/admin/**")
                                        .hasRole("ADMIN")
                                        .requestMatchers("/play/**")
                                        .hasAnyRole("ADMIN", "USER")
                                        .anyRequest()
                                        .denyAll())
                .formLogin(config -> config.loginPage("/login").permitAll());

        return http.build();
    }

    //    @Bean
    //    public PersistentTokenRepository persistentTokenRepository() {
    //        JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
    //        tokenRepo.setDataSource(dataSource);
    //        return tokenRepo;
    //    }
    //
    //    @Bean
    //    RememberMeAuthenticationFilter rememberMeFilter() {
    //        return new RememberMeAuthenticationFilter(rememberMeAuthenticationProvider(),
    // rememberMeServices());
    //    }
    //
    //    @Bean
    //    RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
    //        return new RememberMeAuthenticationProvider(secret);
    //    }
    //
    //
    //    @Bean
    //    RememberMeServices rememberMeServices() {
    //        return new PersistentTokenBasedRememberMeServices(secret, userDetailsService(),
    // persistentTokenRepository());
    //    }
}
