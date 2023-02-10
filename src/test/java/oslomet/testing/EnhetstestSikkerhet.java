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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
    public void test_sjekkLoggetInn() {
        // arrange
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");

        //act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHeiHei");

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void testPersNrogPasswd() {
        //arrange
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("NOT OK");

        //act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHeiHei");

        //assert
        assertEquals("Feil i personnummer eller passord", resultat);
    }

    @Test
    public void testPersonnummer() {
        //act
        String resultat = sikkerhetsController.sjekkLoggInn("1234567890", "HeiHeiHei");
        //assert
        assertEquals("Feil i personnummer", resultat);
    }

    @Test
    public void testPassord() {
        //act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHe");
        //assert
        assertEquals("Feil i passord", resultat);
    }

    @Test
    public void testRegexPersonnummerFeil() {
        String tegn = sikkerhetsController.sjekkLoggInn("!@#¤%&/()=?+-", "HeiHei");
        String kort = sikkerhetsController.sjekkLoggInn("123", "HeiHei");
        String langt = sikkerhetsController.sjekkLoggInn("1234567890101112", "HeiHei");
        String bokstaver = sikkerhetsController.sjekkLoggInn("HvaSkalDuGjøreIDag", "HeiHei");

        assertEquals("Feil i personnummer", tegn);
        assertEquals("Feil i personnummer", kort);
        assertEquals("Feil i personnummer", langt);
        assertEquals("Feil i personnummer", bokstaver);
    }

    @Test
    public void testRegexPassordFeil() {
        String kort = sikkerhetsController.sjekkLoggInn("12345678901", "Hei");
        String langt = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHei1234567890101112testfunknåpls");
        String tom = sikkerhetsController.sjekkLoggInn("12345678901", "");

        assertEquals("Feil i passord", kort);
        assertEquals("Feil i passord", langt);
        assertEquals("Feil i passord", tom);
    }

    @Test
    public void testBrukerinnlogget() {
        session.setAttribute("Innlogget", "12345678901");

        String resultat = sikkerhetsController.loggetInn();

        assertEquals("12345678901", resultat);
    }

    @Test
    public void brukerIkkeLoggetInn() {
        session.setAttribute(null, null);

        String resultat = sikkerhetsController.loggetInn();

        assertNull(resultat);
    }

    @Test
    public void adminLoggetinn() {
        session.setAttribute("Logget inn", "Admin");

        String resultat = sikkerhetsController.loggInnAdmin("Admin", "Admin");

        assertEquals("Logget inn", resultat);
    }

    @Test
    public void adminIkkeLoggetInn() {
        session.setAttribute("Ikke logget inn", null);

        String resultat = sikkerhetsController.loggInnAdmin("", "");

        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void testLoggUt() {
        session.setAttribute("Innlogget", null);
        sikkerhetsController.loggUt();
        String resultat = sikkerhetsController.loggetInn();
        assertNull(resultat);
    }
}



