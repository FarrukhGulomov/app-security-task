package uz.pdp.online.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import uz.pdp.online.repository.UserRepository;
import uz.pdp.online.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {





    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .logout();
        return http.build();

    }

@Bean
public UserDetailsService userDetailsService(){
   // SUPER_ADMIN,MODERATOR,OPERATOR,CUSTOMER
    UserDetails customer = User.builder().username("customer")
            .password(passwordEncoder().encode("customer"))
            .roles("CUSTOMER").build();
    UserDetails operator = User.builder().username("operator")
            .password(passwordEncoder().encode("operator"))
            .roles("OPERATOR").build();
    UserDetails superAdmin = User.builder().username("super_admin")
            .password(passwordEncoder().encode("super_admin"))
            .roles("SUPER_ADMIN").build();
    UserDetails moderator=User.builder().username("moderator")
            .password(passwordEncoder().encode("moderator"))
            .roles("MODERATOR").build();


    return new InMemoryUserDetailsManager(customer,superAdmin,moderator,operator);

}


@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
}
}
