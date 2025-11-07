package com.sde.spbsecurityinmemory.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class InMemorySecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){

        /*This function create the user object with fields/values  */
        UserDetails admin=User.withUsername("emp1").roles("admin").password(passwordEncoder.encode("emp1_")).build();
        
        /*This function created the user object empty then fills the details in it */
        UserDetails manager=User.builder().username("emp2").roles("manager").password(passwordEncoder.encode("emp2_")).build();

        /*this below function will first clone the manager user details then overwrite the existing fields like roles ,passswords ,username */
        UserDetails user=User.withUserDetails(manager).username("emp3").roles("user").password(passwordEncoder.encode("emp3_")).build();
        return new InMemoryUserDetailsManager(user,manager,admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{

        httpSecurity.authorizeHttpRequests(request->request.requestMatchers("/login","/css/**","/js/**").permitAll()
        .requestMatchers("/home","/service/**").hasAnyRole("user","admin","manager")
        .requestMatchers("/configuration/**").hasAnyRole("admin","manager")
        .requestMatchers("officeFiles/**").hasRole("admin")
        .requestMatchers("/onlyManager/**").hasRole("manager")
        .requestMatchers("/onlyUser/**").hasRole("user")
        .anyRequest().authenticated())
        .formLogin(form->form.defaultSuccessUrl("/home", true).permitAll())
        .logout(form->form.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).permitAll());
        return httpSecurity.build();
    }

}
