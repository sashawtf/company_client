package company.security;

import company.entity.User;
import company.entity.UserRegistrationDto;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
   
   User save(@Valid UserRegistrationDto registrationDto);

   List<User> getAll();
}