package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {
    @InjectMocks
    AdminKontoController adminKontoController;

    @Mock
    private AdminRepository repository;

    @Mock
    Sikkerhet sjekk;

    @Test
    public void test_hentAlleKonti() {
        //arrange
        List<Transaksjon> betalinger = new ArrayList<>();
        Transaksjon betaling1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon betaling2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");
        betalinger.add(betaling1);
        betalinger.add(betaling2);

        List<Konto> kontoer = new ArrayList<>();
        Konto enKonto = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", betalinger);
        kontoer.add(enKonto);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.hentAlleKonti()).thenReturn(kontoer);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //assert
        assertEquals(kontoer, resultat);
    }

    @Test
    public void testHentAllekontiFeil() {
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //assert
        assertNull(resultat);
    }

    @Test
    public void registrerKonto_loggetInn() {
        // arrange
        List<Transaksjon> betalinger = new ArrayList<>();
        Transaksjon betaling1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon betaling2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");
        betalinger.add(betaling1);
        betalinger.add(betaling2);

        Konto konto = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKonto(any())).thenReturn("OK");

        // act
        String resultat = adminKontoController.registrerKonto(konto);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKonto_ikkeLoggetInn() {
        // arrange
        List<Transaksjon> betalinger = new ArrayList<>();
        Transaksjon betaling1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon betaling2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");
        betalinger.add(betaling1);
        betalinger.add(betaling2);

        Konto konto = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.registrerKonto(konto);

        // assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void slettKonto_LoggetInn_OK() {
        //arrange
        List<Transaksjon> betalinger = new ArrayList<>();
        Transaksjon betaling1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon betaling2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");
        betalinger.add(betaling1);
        betalinger.add(betaling2);

        Konto konto = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", betalinger);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.slettKonto(any())).thenReturn("OK");

        //act
        String resultat = adminKontoController.slettKonto("105010123456");

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void slettKonto_IkkeLoggetInn() {
        //arrange
        List<Transaksjon> betalinger = new ArrayList<>();
        Transaksjon betaling1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon betaling2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");
        betalinger.add(betaling1);
        betalinger.add(betaling2);

        Konto konto = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", betalinger);


        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.slettKonto("105010123456");

        //assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void endreKonto_loggetInn() {
        //arrange
        List<Transaksjon> betalinger = new ArrayList<>();
        Transaksjon betaling1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon betaling2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");
        betalinger.add(betaling1);
        betalinger.add(betaling2);

        Konto konto = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", betalinger);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKonto(any(Konto.class))).thenReturn("OK");

        //act
        String resultat = adminKontoController.endreKonto(konto);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKonto_ikkeLoggetInn() {
        //arrange
        List<Transaksjon> betalinger = new ArrayList<>();
        Transaksjon betaling1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon betaling2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");
        betalinger.add(betaling1);
        betalinger.add(betaling2);

        Konto konto = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", betalinger);

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.endreKonto(konto);

        //assert
        assertEquals("Ikke innlogget", resultat);
    }
}
