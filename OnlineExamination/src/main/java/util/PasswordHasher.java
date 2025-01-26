package util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "password123"; // Replace with your desired password
        String hashedPassword = encoder.encode(password);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
