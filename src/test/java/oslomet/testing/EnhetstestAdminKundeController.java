package oslomet.testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {
    @InjectMocks
    AdminKundeController adminKundeController;

    @Mock
    private AdminRepository repository;

    @Mock
    Sikkerhet sjekk;

    @Test
    public void test_HentAlleok() {
        List<Kunde> kunder = new ArrayList<>();
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        kunder.add(enKunde);
        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.hentAlleKunder()).thenReturn(kunder);
        List<Kunde> result = adminKundeController.hentAlle();
        assertEquals(kunder, result);
    }
    @Test
    public void test_HentAllefeil(){
        List<Kunde> resultat = adminKundeController.hentAlle();
        Assert.assertNull(resultat);
    }
    @Test
    public void lagre_loggetInn() {
        // arrange
        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");
        when(sjekk.loggetInn()).thenReturn("01010110523");

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertNull(resultat);

    }
    @Test
    public void lagre_ikkeLoggetInn(){
        // arrange
        Kunde kunde1 = new Kunde("01010110523","Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertEquals("Ikke logget inn",resultat);

    }

    @Test
    public void endreKunde_LoggetInn() {
        //arrange
        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");
        when(sjekk.loggetInn()).thenReturn("01010110523");

        //act
        String resultat = adminKundeController.endre(kunde1);

        //assert
        assertNull(resultat);
    }

    @Test
    public void endreKunde_IkkeLoggetInn(){
        // arrange
        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "22224444", "HeiHei");
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.endre(kunde1);

        // assert
        assertNull(null);
    }
    @Test
    public void slett_loggetInn(){
        // arrange
        String personnummer = new String("567676");
        when(sjekk.loggetInn()).thenReturn("345242");

        // act
        String resultat = adminKundeController.slett(personnummer);

        // assert
        assertNull(resultat);

    }
    @Test
    public void slett_ikkeLoggetInn(){
        // arrange
        String persnummer = new String("567676");
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.slett(null);

        // assert
        assertNull(null);
       }

}
