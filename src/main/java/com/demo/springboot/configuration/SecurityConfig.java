package com.demo.springboot.configuration;

import com.demo.springboot.security.MyUserDetailsService;
import com.demo.springboot.security.MyUsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 〈一句话功能简述〉
 *
 * @author wanghu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new MyUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean());
    }



    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilter(new MyUsernamePasswordAuthenticationFilter())
                .authorizeRequests().antMatchers("/css/**",
                        "/font/**",
                        "/fonts/**",
                        "/images/**",
                        "/js/**",
                        "/locale/**",
                        "/skin/**",
                        "/themes/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/loginIndex").loginProcessingUrl("/login").successForwardUrl("/init")
                .failureForwardUrl("/error").permitAll()
                .and().logout().permitAll()
                .and().csrf().disable();

//        http.logout().logoutSuccessUrl("/login"); // 退出默认跳转页面 (5)
    }
}
