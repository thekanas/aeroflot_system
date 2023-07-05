package by.stolybko.web.config;


import by.stolybko.service.PersonService;
import by.stolybko.web.util.PagesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static by.stolybko.web.util.PagesUtil.*;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String VIEW_PATH = "/WEB-INF/views/*";
    private final PersonService personService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers( VIEW_PATH,"/", "/login").permitAll()
                        //.anyRequest().authenticated())
                        .requestMatchers("/person", "/persons").hasRole("admin")
                        .requestMatchers("/airplanes", "/flights").hasAnyRole("EMPLOYEE", "ADMIN")
                        .anyRequest().hasAnyRole("ADMIN", "EMPLOYEE"))


                //.httpBasic(withDefaults());
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/persons")
                        .failureUrl("/login?error"));

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(personService);
        return authProvider;
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }
}
