package oslomet.testing;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AdminKontoTest {
    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    Sikkerhet sjekk = new Sikkerhet();
    @Test
    public void hentKonti_LoggetInn() {
        // arrange
        List<Kunde> kunde = new ArrayList<>();
        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "22224444", "HeiHei");
        Kunde kunde2 = new Kunde("12345678901", "Per", "Hansen", "Osloveien 82", "1234", "12345678", "HeiHei");
        kunde.add(kunde1);
        kunde.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentAlleKunder()).thenReturn(kunde);

        // act
        List<Kunde> resultat = repository.hentAlleKunder();

        // assert
        assertEquals(kunde, resultat);
    }
    @Test
    public void testRegistrerKonto() {
        when(sjekk.loggetInn()).thenReturn("12345678910");
        Konto konto = new Konto();
        when(repository.registrerKonto(konto)).thenReturn("OK");
        assertEquals("OK", adminKontoController.registrerKonto(konto));
    }
}
