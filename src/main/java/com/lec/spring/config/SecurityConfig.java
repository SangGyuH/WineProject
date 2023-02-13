package com.lec.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //비밀번호 암호화를 위한 bean 객체생성.
    @Bean
    public PasswordEncoder encoder(){
        System.out.println("### LOG: PasswordEncoder bean 생성 ###");
        return new BCryptPasswordEncoder();
    }

    //미회원 / 미로그인자 판별 및 보안을 위한 SecurityFilterChain bean 객체 등록 및 사용
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{

        return security
                .csrf(csrf -> csrf.disable())                                                   //CSRF 비활성화

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("").authenticated()                                                        //해당 페이지들은 로그인(인증)이 되어있어야한다
                        .requestMatchers("").hasAnyRole("ADMIN", "SILVER", "GOLD", "DIAMOND")                   //해당 페이지들은 로그인(인증) 및 권한(ADMIN, SILVER, GOLD, DIAMOND)이 필요하다.
                        .requestMatchers("").hasAnyRole("ADMIN")                                                //해당 페이지들은 관리자만 접속 가능하다
                        .anyRequest().permitAll()                                                                           //위의 페이지들 외엔 로그인(인증) / (권한) 이 없어도 접속 가능하다.
                )

                .formLogin(form -> form
                        //TODO
                        /*
                        .loginPage("/user/login")    //로그인이 필요한 상황 발생시 매개변수의 url(로그인 폼)으로 request가 발생한다.
                        .loginProcessingUrl("/user/login")  // "/user/login" url 로 POST request 가 들어오면 시큐리티가 낚아채서 처리, 대신 로그인을 진행해준다.
                                                            // 이와 같이 하면 Controller 에서 /user/login (POST) 를 굳이 만들지 않아도 된다!
                                                            // 위 요청이 오면 자동으로 UserDetailsService 타입 빈객체의 loadUserByUsername() 가 실행되어 인증여부 확인진행 <- 이를 제공해주어야 한다.

                        .defaultSuccessUrl("/")     // '직접 /login' → /loginOk 에서 성공하면 "/" 로 이동시키기
                                                    // 만약 다른 특정페이지에 진입하려다 로그인 하여 성공하면 해당 페이지로 이동 (너무 편리!)
                        */
                        .successHandler(new CustomLoginSuccessHandler("/vvine"))
                        .failureHandler(new CustomLoginFailureHandler())
                )

                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(false)
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                )

                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler(new CustomAccessDeniedHandler())                       //권한(Authroization) 오류 발생시 실행할 코드
                )
                .build();
    }

}
