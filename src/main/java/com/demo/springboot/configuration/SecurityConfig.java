package com.demo.springboot.configuration;

import com.demo.springboot.security.MyUserDetailsService;
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
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/css/**",
                        "/font/**",
                        "/fonts/**",
                        "/images/**",
                        "/js/**",
                        "/locale/**",
                        "/skin/**",
                        "/themes/**")
                .permitAll().and() //默认不拦截静态资源的url pattern
                .formLogin().loginPage("/login")// 登录url请求路径 (3)
                .defaultSuccessUrl("/init").and() // 登录成功跳转路径url(4)
                .authorizeRequests().antMatchers("/login").permitAll().and()

                .logout().permitAll();

//        http.logout().logoutSuccessUrl("/login"); // 退出默认跳转页面 (5)
    }
}
