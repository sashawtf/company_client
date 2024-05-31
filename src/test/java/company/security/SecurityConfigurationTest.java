package company.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SecurityConfigurationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testPasswordEncoderBean() {
        BCryptPasswordEncoder passwordEncoder = context.getBean(BCryptPasswordEncoder.class);
        assertNotNull(passwordEncoder);
    }

    @Test
    public void testSecurityFilterChainBean() {
        SecurityFilterChain securityFilterChain = context.getBean(SecurityFilterChain.class);
        assertNotNull(securityFilterChain);
    }
}
