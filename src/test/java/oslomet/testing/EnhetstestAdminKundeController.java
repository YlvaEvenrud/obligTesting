package oslomet.testing;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EnhetstestAdminKundeController {
    @InjectMocks
    private AdminKundeController adminKundeController;

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
    public void lagre_loggetInn(){
        // arrange
        Kunde kunde1 = new Kunde("01010110523","Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");

        //Mockito.when(sjekk.loggetInn()).thenReturn("OK");
        //Mockito.when(repository.lagreKunde((any(Kunde.class)))).thenReturn("OK");
        //Mockito.when(repository.registrerKunde((any(Kunde.class)))).thenReturn("OK");
       when(sjekk.loggetInn()).thenReturn("OK");

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertEquals("OK",resultat);

    }
    @Test
    public void lagre_ikkeLoggetInn(){
        // arrange
        Kunde kunde1 = new Kunde("01010110523","Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertNull(resultat);
    }

    @Test
    public void slett_loggetInn(){
        // arrange
        String personnummer = new String("567676");
        when(sjekk.loggetInn()).thenReturn("345242");

        // act
        String resultat = adminKundeController.slett(personnummer);

        // assert
        assertEquals("OK",resultat);

    }
    @Test
    public void slett_ikkeLoggetInn(){
        // arrange
        String persnummer = new String("567676");
        when(sjekk.loggetInn()).thenReturn(null);

        // act


        // assult
       }

}
