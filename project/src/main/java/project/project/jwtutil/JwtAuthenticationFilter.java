package project.project.jwtutil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // "Bearer " kısmını çıkar
            try {
                username = jwtUtil.extractUsername(jwt);  // JWT'den kullanıcı adını al

                LOGGER.info("JWT token extracted: {}", jwt);
                LOGGER.info("Username extracted from token: {}", username);

            } catch (io.jsonwebtoken.SignatureException e) {
                LOGGER.error("JWT signature does not match: {}", jwt, e);
            } catch (io.jsonwebtoken.MalformedJwtException e) {
                LOGGER.error("Invalid JWT token format: {}", jwt, e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token format");
                return;
            }
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);


            boolean isTokenValid = jwtUtil.validateToken(jwt, userDetails.getUsername());
            LOGGER.info("Is token valid? {}", isTokenValid);

            if (isTokenValid) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());


                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                LOGGER.info("Authentication set in SecurityContext for user: {}", username);
            } else {

                LOGGER.warn("Invalid or expired JWT token for user: {}", username);
                response.sendRedirect("/login");  // Token geçersizse login sayfasına yönlendir
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
