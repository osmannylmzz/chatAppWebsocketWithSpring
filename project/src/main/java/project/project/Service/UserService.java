package project.project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.project.model.User;
import project.project.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Bu kullanıcı adı zaten alınmış.");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }
    public Optional<User> getUserByUsername(String username) {
        System.out.println("Kullanıcı sorgulanıyor: " + username);
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(value -> System.out.println("Kullanıcı bulundu: " + value));
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(); // Veritabanındaki tüm kullanıcıları alır
    }
    // Kullanıcı adı ile kullanıcıyı bulma işlemi
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);  // Repository'den kullanıcıyı getir
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }



}
