
package net.codejava;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }  
    @Bean 
    public DaoAuthenticationProvider authenticationProvider() {
    	 DaoAuthenticationProvider authProvider = new
    	 DaoAuthenticationProvider(userDetailsService);
    	 authProvider.setPasswordEncoder(passwordEncoder()); 
    	 return authProvider; 
    	 }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/list_users").authenticated()   // ✅ protect /users
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")      // optional if you have custom login page
                .usernameParameter("email")
                .defaultSuccessUrl("/list_users")  // ✅ redirect correctly
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}
