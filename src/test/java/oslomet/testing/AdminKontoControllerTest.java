package oslomet.testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminKontoControllerTest {
    @InjectMocks
    AdminKontoController adminKontoController;

    @Mock
    private AdminRepository repository;

    @Mock
    Sikkerhet sjekk;

    @Test
    public void test_hentAlleKonti() {
        List<Konto> kontoer = new ArrayList<>();
        Konto enKonto = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", null);
        kontoer.add(enKonto);
        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.hentAlleKonti()).thenReturn(kontoer);
        List<Konto> result = adminKontoController.hentAlleKonti();
        assertEquals(kontoer, result);
    }
    @Test
    public void testHentAllekontiFeil() {
        List<Konto> resultat = adminKontoController.hentAlleKonti();
        Assert.assertNull(resultat);
    }
    @Test
    public void registrerKonto_loggetInn() {
        // arrange
        //String konto = new String("105010123456");
        Konto konto = new Konto();

        when(sjekk.loggetInn()).thenReturn("01010110523");

        //when(repository.registrerKonto(Konto kon)).thenReturn(konto);
        when(repository.registrerKonto(any())).thenReturn("OK");


        // act
        String resultat = adminKontoController.registrerKonto(konto);

        // assert
        assertEquals("OK", resultat);
    }
    @Test
    public void registrerKonto_ikkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.registrerKonto(null);

        // assert
        assertEquals("Ikke innlogget",resultat);
    }

    @Test
    public void slettKonto_LoggetInn(){
        //arrange

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.slettKonto(anyString())).thenReturn("OK");

        //act

        String resultat = adminKontoController.slettKonto("105010123456");

        //assert

        assertEquals("OK", resultat);
    }

    @Test
    public void slettKonto_IkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.slettKonto("105010123456");

        //assert
        assertEquals("Ikke innlogget", resultat);
    }
}
