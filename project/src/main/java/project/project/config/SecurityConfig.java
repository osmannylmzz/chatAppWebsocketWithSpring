package project.project.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import project.project.Service.CustomUserDetailsService;
import project.project.jwtutil.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/api/auth/register").permitAll() // Kayıt ve giriş işlemleri için izin ver
                        .requestMatchers("/call").permitAll()
                        .requestMatchers("/api/chat").permitAll()
                        .requestMatchers("api/chat.html").permitAll()
                        .requestMatchers("/api/chat/chat").permitAll()
                        .requestMatchers("/api/chat/**").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/messages/send").permitAll()
                        .requestMatchers("/api/chat/messages/**").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("https://localhost:8443/ws").permitAll()
                        .requestMatchers("https://localhost:8443/api/login").permitAll()
                        .anyRequest().permitAll() // Diğer tüm isteklere kimlik doğrulama zorunluluğu getir
                )
                .logout(logout -> logout.permitAll()) // Çıkış işlemleri için izin ver
                .cors(cors -> cors.configurationSource(request -> {
                    var configuration = new org.springframework.web.cors.CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("http://localhost:63342")); // Frontend'inizin adresi burada belirtilmeli (örneğin localhost:63342)
                    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // İzin verilen HTTP yöntemleri
                    configuration.setAllowCredentials(true);
                    return configuration;
                }))
                // HTTPS zorunluluğu
                .requiresChannel(channel -> channel.anyRequest().requiresSecure());


        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
//@Bean
//public AuthenticationManager authManager(AuthenticationConfiguration configuration) throws Exception {
//    return configuration.getAuthenticationManager();
//}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
