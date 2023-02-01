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
    Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_loggetInn(){
        Mockito.when(this.repository.hentAlleKonti()).thenReturn((Object)null);
        List<Konto> resultat = this.adminKontoController.hentAlleKonti();
        Assert.assertNull(resultat);
    }
    @Test
    public void testRegistrerKonto() {
        when(sjekk.loggetInn()).thenReturn("12345678910");
        Konto konto = new Konto();
        when(repository.registrerKonto(konto)).thenReturn("OK");
        assertEquals("OK", adminKontoController.registrerKonto(konto));
    }
}
