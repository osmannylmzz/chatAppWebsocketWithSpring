//package project.project.config;
//
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import project.project.jwtutil.JwtUtil;
//
//@Component
//public class AuthChannelInterceptor implements ChannelInterceptor {
//
//    private final JwtUtil jwtUtil;
//
//    public AuthChannelInterceptor(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        String token = accessor.getFirstNativeHeader("Authorization");
//
//        if (token != null && token.startsWith("Bearer ")) {
//            String jwt = token.substring(7); // "Bearer " kısmını çıkar
//            if (jwtUtil.validateToken(jwt, jwtUtil.extractUsername(token))) {
//                String username = jwtUtil.extractUsername(jwt);
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(username, null, null);
//
//                // Log for debugging
//                System.out.println("Kullanıcı doğrulandı: " + username);
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            } else {
//                System.err.println("JWT doğrulama başarısız!");
//                throw new RuntimeException("JWT doğrulama başarısız!");
//            }
//        } else {
//            System.err.println("Authorization header eksik veya yanlış!");
//            throw new RuntimeException("Authorization header eksik veya yanlış!");
//        }
//
//        return message;
//    }
//}
