package net.javaguides.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/registration**", "/login**", "/css/**", "/js/**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/user/**").hasRole("USER") // 仅允许 USER 角色访问 /user/** 路径
				.anyRequest().authenticated()
				.and()
				.csrf().disable()
				.formLogin()
				.loginPage("/login")
				.successHandler(authenticationSuccessHandler()) // 自定义的成功处理程序
				.permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/login") // 设定登出成功后重定向到 /login
				.permitAll();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
				// 检查用户的角色并定向到相应页面
				if (authentication.getAuthorities().stream()
						.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
					response.sendRedirect("/admin"); // 重定向到管理员页面
				} else if (authentication.getAuthorities().stream()
						.anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"))) {
					response.sendRedirect("/user"); // 重定向到用户主页
				} else {
					response.sendRedirect("/"); // 如果没有匹配角色，重定向到首页
				}
			}
		};
	}
}
