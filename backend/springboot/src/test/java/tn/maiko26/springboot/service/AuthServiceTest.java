@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;
    
    @MockBean
    private UserRepository userRepository;
    
    @Test
    void loginSuccess() {
        // Given
        String email = "test@test.com";
        String password = "password";
        User mockUser = new User(email, 
            new BCryptPasswordEncoder().encode(password), "Test User");
        mockUser.setIsVerified(true);
        
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        
        // When
        String token = authService.login(email, password);
        
        // Then
        assertNotNull(token);
    }
    
    @Test
    void loginFailsWithWrongPassword() {
        // Given
        String email = "test@test.com";
        User mockUser = new User(email, 
            new BCryptPasswordEncoder().encode("rightpass"), "Test User");
        mockUser.setIsVerified(true);
        
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        
        // When/Then
        assertThrows(Exception.class, 
            () -> authService.login(email, "wrongpass"));
    }
    
    @Test
    void accountLocksAfterFiveFailedAttempts() {
        // Given
        String email = "test@test.com";
        User mockUser = new User(email, 
            new BCryptPasswordEncoder().encode("rightpass"), "Test User");
        mockUser.setIsVerified(true);
        
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        
        // When
        for(int i = 0; i < 5; i++) {
            assertThrows(Exception.class, 
                () -> authService.login(email, "wrongpass"));
        }
        
        // Then
        verify(userRepository, times(1)).save(argThat(user -> 
            user.isLocked() && user.getFailedAttempts() == 5));
    }
    
    @Test
    void registerSuccess() {
        // Given
        String email = "new@test.com";
        String password = "password";
        String name = "New User";
        
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        
        // When
        String token = authService.register(email, password, name);
        
        // Then
        assertNotNull(token);
        verify(userRepository).save(argThat(user -> 
            user.getEmail().equals(email) && 
            user.getName().equals(name) &&
            !user.getIsVerified()));
    }
}
