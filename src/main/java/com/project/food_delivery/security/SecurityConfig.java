package com.project.food_delivery.security;

import com.project.food_delivery.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    //đối tượng sử dụng cho việc gọi hàm kiểm tra đăng nhập
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }

    //đối tượng dùng để mã hóa password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //đối tượng filter của spring security (quy định các rule liên quan tới bảo mật và quyền truy cập)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //antMatchers("/home") => những request truy cập vào link được chỉ định
        //anyRequest() => bất kỳ request truy cập vào link nào
        //permitAll() => cho phép truy cập full quyền vào link
        //authenticated() => bắt buộc phải chứng thực khi truy cập vào link
        //hasRole("ADMIN") => những request có role thích hợp mới được truy cập
        //hasAnyRole("ADMIN,LEADER") => những request có role thích hợp mới được truy cập
        http.csrf()
                .disable()
                .authorizeRequests()//những request phải thông qua cấu hình chứng thực
                .antMatchers("/signin").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }
}