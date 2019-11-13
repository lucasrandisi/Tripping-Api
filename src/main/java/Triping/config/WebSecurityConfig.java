package Triping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
            .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/interest/all").permitAll()
                .antMatchers(HttpMethod.GET, "/*/profile").permitAll()
                .antMatchers(HttpMethod.GET, "/*/followed").permitAll()
                .antMatchers(HttpMethod.GET, "/*/followers").permitAll()
                .antMatchers(HttpMethod.GET, "/*/trips/*").permitAll()
                .antMatchers(HttpMethod.GET,"/").permitAll()

                .antMatchers(HttpMethod.POST, "/auth", "/user/register" ).permitAll()

                .anyRequest().authenticated()
            .and()
                .csrf().disable();
    }
}