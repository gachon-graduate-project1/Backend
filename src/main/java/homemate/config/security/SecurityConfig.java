package homemate.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import homemate.config.jwt.filter.JwtFilter;
import homemate.config.jwt.service.JwtService;
import homemate.config.oauth2.handler.OAuth2LoginFailureHandler;
import homemate.config.oauth2.handler.OAuth2LoginSuccessHandler;
import homemate.config.oauth2.service.CustomOAuth2UserService;
import homemate.repository.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(FormLoginConfigurer::disable) // FormLogin 사용 X
                .httpBasic(HttpBasicConfigurer::disable) // httpBasic 사용 X
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )

                // 세션 사용하지 않으므로 STATELESS로 설정
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))



                //== URL별 권한 관리 옵션 ==//
                // 인가 규칙 설정
                .authorizeHttpRequests(authorize ->
                        authorize
                                // 로그인,회원가입은 시큐리티 적용x
                                .requestMatchers(new AntPathRequestMatcher("/oauth2/authorization/kakao")
                                        , new AntPathRequestMatcher("/**")
                                        , new AntPathRequestMatcher("/oauth2/authorization/naver")
                                        , new AntPathRequestMatcher("/login/**")
                                        , new AntPathRequestMatcher("/user/sign-up")
                                        , new AntPathRequestMatcher("/h2-console/**")).permitAll()
                                // 나머지는 시큐리티 적용
                                //.requestMatchers(HttpMethod.OPTIONS, "/**").authenticated()
                                .anyRequest().authenticated()
                )


                //== 소셜 로그인 설정 ==//
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .successHandler(oAuth2LoginSuccessHandler)
                                .failureHandler(oAuth2LoginFailureHandler)
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint.userService(customOAuth2UserService))
                );
        http
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession(false);
                            if (session != null) {
                                session.invalidate();
                            }
                        })
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/");
                        })
                        .deleteCookies("remember-me")
                );


        // 원래 스프링 시큐리티 필터 순서가 LogoutFilter 이후에 로그인 필터 동작
        // 따라서, LogoutFilter 이후에 우리가 만든 필터 동작하도록 설정
        // 순서 : LogoutFilter -> JwtFilter -> UsernamePasswordAuthenticationFilter
        http.addFilterAfter(new UsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



    @Bean
    public JwtFilter jwtFilter() {
        JwtFilter jwtFilter = new JwtFilter(jwtService, userRepository);
        return jwtFilter;
    }

}
