package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
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
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;


    @Test
    public void hentTransaksjoner_loggetInn() {
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto konto1 = new Konto("01010110523", "105010123456", 720, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konto1);


        // act
        Konto resultat = bankController.hentTransaksjoner("", "", "");

        // assert
        assertEquals(konto1, resultat);
    }

    @Test
    public void hentTransaksjoner_ikkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Konto resultat = bankController.hentTransaksjoner("", "", "");

        // assert
        assertNull(resultat);
    }

    //TOR
    @Test
    public void hentKonti_LoggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    //TOR
    @Test
    public void hentKonti_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_LoggetInn() {
        // arrange
        List<Konto> saldi = new ArrayList<>();

        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);

        saldi.add(konto1);
        saldi.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentSaldi(anyString())).thenReturn(saldi);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(saldi, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }

    @Test
    public void registrerBetaling_LoggetInn() {
        // arrange
        Transaksjon betaling = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerBetaling(betaling)).thenReturn("OK");

        // act
        String resultat = bankController.registrerBetaling(betaling);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn() {
        // arrange
        Transaksjon betaling = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "105010123456", "1");

        // act
        String resultat = bankController.registrerBetaling(betaling);

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentBetalinger_LoggetInn() {
        // arrange
        List<Transaksjon> betalinger = new ArrayList<>();

        Transaksjon betaling1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon betaling2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");

        betalinger.add(betaling1);
        betalinger.add(betaling2);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.hentBetalinger(anyString())).thenReturn(betalinger);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertEquals(betalinger, resultat);
    }

    @Test
    public void hentBetalinger_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);
    }

    @Test
    public void utforBetaling_LoggetInn() {
        //arrange
        Transaksjon betaling1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon betaling2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");

        List<Transaksjon> betalinger = new ArrayList<>();

        betalinger.add(betaling1);
        betalinger.add(betaling2);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.utforBetaling(anyInt())).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(betalinger);

        //act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        //assert
        assertEquals(betalinger, resultat);
    }

    @Test
    public void utforBetaling_IkkeLoggetInn() {
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        //assert
        assertNull(resultat);
    }

    //TOR
    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    //TOR
    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }


    @Test
    public void endreKundeInfo_loggetInn_OK() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKundeInfo_IkkeLoggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertNull(resultat);
    }


}


