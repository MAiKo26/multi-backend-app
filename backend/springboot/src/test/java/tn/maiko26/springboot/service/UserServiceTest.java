package tn.maiko26.springboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@test.com");
        testUser.setName("Test User");
    }

    @Test
    void getAllUsers_ShouldReturnUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));
        
        List<User> users = userService.getAllUsers();
        
        assertFalse(users.isEmpty());
        assertEquals(testUser.getEmail(), users.get(0).getEmail());
    }
}
