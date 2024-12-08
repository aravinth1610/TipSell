package org.tipSell.configure;

import static org.tipSell.authKey.Constant.AuthKeyConstant.PUBLIC_URLS;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.tipSell.authKey.Constant.AuthKeyConstant;
import org.tipSell.handlerException.CustomAccessDeniedHandler;
import org.tipSell.handlerException.CustomAuthenticationEntryPoint;
import org.tipSell.securityFilters.CorsConfigurationFilter;
import org.tipSell.securityFilters.TokenValidationFilter;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebSecurityConfigure {

//	RouteValidator
//	private final CorsConfigurationFilter corsConfigurationFilter;
//	private final CustomAccessDeniedHandler accessDeniedHandler;
//	private final CustomAuthenticationEntryPoint authenticationEntryPoint;
	private final TokenValidationFilter tokenValidationFilter;

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				.cors(corsCustomize -> corsCustomize.configurationSource(new CorsConfigurationFilter()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(this.tokenValidationFilter, BasicAuthenticationFilter.class)
				.authorizeHttpRequests(auth -> {
//					this.roleBase.getPermissions().forEach(permission -> {
//					 try {
//					auth.requestMatchers(HttpMethod.valueOf(permission.getMethod()), permission.getEndpoints())
//							.hasAnyAuthority(permission.getRoles().split(","));
//					
//					 } catch (Exception e) {
//	                        throw new RuntimeException("Failed to configure authorization", e);
//	                    }
//				});
//				 auth.requestMatchers(AppConstant.PUBLIC_URLS).permitAll().anyRequest().authenticated();
//--------------------------------------------------------------------------------------------------------------------				
					auth.requestMatchers(HttpMethod.GET, "/realms/key/demo/sec").hasAnyAuthority("USER", "ADMIN")
					.requestMatchers("/clients/{clientId}/roles/**").hasAnyAuthority("access_token","USER", "ADMIN")
							.requestMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated();
//------------------------------------------------------------------------------------------------------------------------				
				})
				.exceptionHandling(exception -> exception.accessDeniedHandler(new CustomAccessDeniedHandler())
						.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
				.formLogin(formLogin -> formLogin.disable()).httpBasic(httpBasic -> httpBasic.disable());
		return http.build();
	}

}
