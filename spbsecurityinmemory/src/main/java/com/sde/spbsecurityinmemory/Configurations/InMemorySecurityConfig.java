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
        UserDetails user=User.withUsername("emp1").roles("user").password(passwordEncoder.encode("emp1_")).build();
        
        /*This function created the user object empty then fills the details in it */
        UserDetails manager=User.builder().username("emp2").roles("manager","user").password(passwordEncoder.encode("emp2_")).build();

        /*this below function will first clone the manager user details then overwrite the existing fields like roles ,passswords ,username */
        UserDetails admin=User.withUserDetails(manager).roles("admin","manager","user").username("emp3").password(passwordEncoder.encode("emp3_")).build();
        return new InMemoryUserDetailsManager(user,manager,admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.authorizeHttpRequests(request->request.requestMatchers("/","/login","/css/**","/js/**").permitAll()
        .requestMatchers("/admin/**").hasRole("admin")
        .requestMatchers("/manager/**").hasAnyRole("admin","manager")
        .requestMatchers("/user/**").hasAnyRole("admin","manager","user")
        .anyRequest().authenticated()
        )
        .formLogin(form->form.loginPage("/login").defaultSuccessUrl("/",true).permitAll())
        .logout(form->form.logoutSuccessUrl("/login?logout").permitAll()
        );
        return httpSecurity.build();
    }

}
