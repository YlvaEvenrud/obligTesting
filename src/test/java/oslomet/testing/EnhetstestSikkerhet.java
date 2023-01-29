package oslomet.testing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)

public class EnhetstestSikkerhet {
    @InjectMocks
    private Sikkerhet sikkerhetsController;

    @Mock
    private BankRepository repository;

    @Mock
    MockHttpSession session;

    @Before
    public void initSession() {
        Map<String, Object> attributes = new HashMap<>();

        doAnswer((Answer<Object>) invocation -> {
            String key = (String) invocation.getArguments()[0];
            return attributes.get(key);
        }).when(session).getAttribute(anyString());

        doAnswer((Answer<Object>) invocation -> {
            String key = (String) invocation.getArguments()[0];
            Object value = invocation.getArguments()[1];
            attributes.put(key, value);
            return null;
        }).when(session).setAttribute(anyString(), any());
    }
    @Test
    public void test_sjekkLoggetInn(){
        // arrange
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("OK");
        //act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHeiHei");
        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void test_LoggetInn(){
        //arrange
        session.setAttribute("Innlogget", "12345678901");
        //act
        String resultat = sikkerhetsController.loggetInn();
        //assert
        assertEquals("12345678901", resultat);
    }


    @Test
    public void testLoggInnAdmin_SuccessfulLogin() {
        String expectedResult = "Logget inn";
        when(session.getAttribute("Innlogget")).thenReturn("Admin");

        String result = sikkerhetsController.loggInnAdmin("Admin", "Admin");

        assertEquals(expectedResult, result);
    }

    @Test
    public void testLoggInnAdmin_UnsuccessfulLogin() {
        String expectedResult = "Ikke logget inn";
        when(session.getAttribute("Innlogget")).thenReturn(null);

        String result = sikkerhetsController.loggInnAdmin("Admin", "WrongPassword");

        assertEquals(expectedResult, result);
    }
}



