package project.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.project.Repository.UserRepository;
import project.project.Service.UserService;
import project.project.jwtutil.JwtUtil;
import project.project.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63342/") // CORS ayarını yaptık
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequest authenticationRequest) {
        Map<String, String> response = new HashMap<>();
        try {

            Optional<User> userOptional = userRepository.findByUsername(authenticationRequest.getUsername());

            if (userOptional.isEmpty()) {
                response.put("message", "Geçersiz kullanıcı adı");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            User user = userOptional.get();

            if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
                // Şifre eşleşmezse, hata mesajı döndür
                response.put("message", "Geçersiz şifre");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }


            String token = jwtUtil.generateToken(user.getUsername());


            response.put("token", token);
            response.put("username", user.getUsername());  // Kullanıcı adı da döndürülüyor
            response.put("message", "Giriş başarılı");
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("message", "Giriş sırasında bir hata oluştu");
            e.printStackTrace(); // Hatanın detayını konsola yazdır
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/validate-username/{username}")
    public ResponseEntity<?> validateUsername(@PathVariable String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.ok("Kullanıcı adı geçerli");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı adı geçersiz");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            Optional<User> existingUser = userService.findByUsername(authenticationRequest.getUsername());
            if (existingUser.isPresent()) {
                return ResponseEntity.badRequest().body("{\"message\": \"Username already taken\"}");
            }

t
            userService.registerUser(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            return ResponseEntity.ok("{\"message\": \"Registration successful\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"Registration failed: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("register");
    }


    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        return new ModelAndView("login");
    }
    @GetMapping("/home")
    public ModelAndView redirectHome() {
        // Burada home.html sayfasına yönlendiriyoruz
        return new ModelAndView("home");
    }


    public static class AuthenticationRequest {
        private String username;
        private String password;


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
