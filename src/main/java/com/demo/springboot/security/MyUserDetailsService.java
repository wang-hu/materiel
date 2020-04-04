package com.demo.springboot.security;

import com.demo.springboot.domain.security.UserInfo;
import com.demo.springboot.domain.security.UserAuth;
import com.demo.springboot.mapper.UserAuthMapper;
import com.demo.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉
 *
 * @author wanghu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = mapper.selectByLoginName(username);
        Optional.ofNullable(user).orElseThrow(() -> new UsernameNotFoundException(username));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<UserAuth> userAuthorities = userAuthMapper.listUserAuthorities(username);
        for (UserAuth userAuth : userAuthorities) {
            authorities.add(new SimpleGrantedAuthority(userAuth.getAuthority()));
        }

        return new User(username, user.getUserPassword(), true, true, true, true, authorities);
    }
}
