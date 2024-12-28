@SpringBootTest
public class EmailSenderServiceTest {
    @Autowired
    private EmailSenderService emailSenderService;
    
    @MockBean
    private JavaMailSender mailSender;
    
    @Test
    void sendVerificationEmailSuccess() {
        // Given
        String email = "test@test.com";
        String token = "test-token";
        
        // When
        emailSenderService.sendVerificationEmail(email, token);
        
        // Then
        verify(mailSender).send(argThat(message -> {
            SimpleMailMessage simpleMessage = (SimpleMailMessage) message;
            return simpleMessage.getTo()[0].equals(email) &&
                   simpleMessage.getText().contains(token) &&
                   simpleMessage.getSubject().contains("Verify");
        }));
    }
}
