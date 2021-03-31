package hr.tvz.susac.java.web.flight.repository;

import hr.tvz.susac.java.web.flight.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static hr.tvz.susac.java.web.flight.TestStaticUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTests
{
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void findOneByEmail()
    {
        User user = userRepository.findOneByEmail(USER_ONE_EMAIL).orElse(null);

        assertNotNull(user);
        assertEquals(USER_ONE_EMAIL, user.getEmail());
    }

    @Test
    @Order(2)
    public void findOneByUsername()
    {
        User user = userRepository.findOneByUsername(USER_ONE_NAME).orElse(null);

        assertNotNull(user);
        assertEquals(USER_ONE_NAME, user.getUsername());
    }

    @Test
    @Order(3)
    public void save()
    {
        User user = createNewUser();

        user = userRepository.save(user);

        assertNotNull(user);
        assertEquals(USER_THREE_NAME, user.getUsername());
    }
}
