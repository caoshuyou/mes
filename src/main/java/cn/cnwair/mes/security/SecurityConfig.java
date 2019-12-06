package cn.cnwair.mes.security;

import cn.cnwair.mes.security.securityProperties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public SecurityProperties securityProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage(securityProperties.getLoginprocessingurl())
                .loginProcessingUrl("/login")
                .and()
                .authorizeRequests()
                .antMatchers(securityProperties.getLoginprocessingurl(),"/static/**").permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable();
    }
}
