//package com.smn.library_api.security;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//


//======================TELUSKO=====================

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//
//
//
//
//    @Bean
//    public AuthenticationProvider authProvider() {
//        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
//
//        daoAuthProvider.setUserDetailsService(userDetailsService());
////        daoAuthProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); //for no password encryption.
//
//        daoAuthProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//
//        return daoAuthProvider;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.csrf(customizer -> customizer.disable());
//
//        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
//        //if we make session stateless we don't need form login, otherwise it will go to same user nad pass again and again
////        http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
//
//        //it will be state-full session which will give same sessionID
//
//        //to make stateless
//        http.sessionManagement((session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)));
//
//        //if we make it stateless we can not use logout, may be..................
//
//
//        return http.build();
//    }
//
//
//    //Using normal, not prefering Lambda
//    /*
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//
//        Customizer<CsrfConfigurer<HttpSecurity>> custCsrf = new Customizer<CsrfConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(CsrfConfigurer<HttpSecurity> configurer) {
//                configurer.disable();
//            }
//        };
//
//        http.csrf(custCsrf);
//
//        Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> custHttp = new Customizer<AuthorizeHttpRequestsConfigurer<org.springframework.security.config.annotation.web.builders.HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {
//            @Override
//            public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
//
//            }
//        };
//
//        http.authorizeHttpRequests(custHttp);
//        http.httpBasic(Customizer.withDefaults());
//
//        //it will be state-full session which will give same sessionID
//
//        //to make stateless
//
//        http.sessionManagement((session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)));
//
//
//        return http.build();
//    }
//    */
//
//
//    //For multiple user(hard coded not from db)
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        //Without Encoding, we can use this, but not preferred
//        //UserDetails user = User.builder().build();
//
//
//        //Here also we are using deprecated default one, so it is also same as above
//        UserDetails admin = User
//                .withDefaultPasswordEncoder()
//                .username("mdsohil")
//                .password("smn123")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails sohil = User
//                .withDefaultPasswordEncoder()
//                .username("mdsohil")
//                .password("smn123")
//                .roles("USER")
//                .build();
//        //return new InMemoryUserDetailsManager(admin);
//        return new InMemoryUserDetailsManager(admin, sohil);
//    }
//
//}
