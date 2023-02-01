package oslomet.testing;

import org.junit.Test;
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
        // arrange
        when(sjekk.loggetInn()).thenReturn("12345678910");

        List<Konto> konti = new ArrayList<>();
        konti.add(new Konto());
        konti.add(new Konto());

        when(repository.hentAlleKonti()).thenReturn(konti);
        // act

        // assert
        assertEquals(konti, adminKontoController.hentAlleKonti());
    }
}
