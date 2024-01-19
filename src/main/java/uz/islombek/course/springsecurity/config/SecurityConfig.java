package uz.islombek.course.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.islombek.course.springsecurity.service.PersonDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JWTFilter jwtFilter;
    private final PersonDetailService personDetailService;

    @Autowired
    public SecurityConfig(JWTFilter jwtFilter, PersonDetailService personDetailService) {
        this.jwtFilter = jwtFilter;

        this.personDetailService = personDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(configurer ->
                                configurer
//                                .requestMatchers("/admin").hasRole("ADMIN") //ROLE_
                                        .requestMatchers("/auth/v1/login","/auth/v1/registration","/error")
                                        .permitAll()
                                        .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
                        .anyRequest().hasAnyRole("USER", "ADMIN")
                )
                .formLogin(form ->
                        form
                                .loginPage("/v1/auth/login")
                                .usernameParameter("username")
                                .passwordParameter("parol")
                                .loginProcessingUrl("/process_login")
                                .defaultSuccessUrl("/hello", true)
                                .failureUrl("/v1/auth/login?error")
                                .permitAll()
                ).logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/v1/auth/login"))
                .sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(personDetailService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }


}
