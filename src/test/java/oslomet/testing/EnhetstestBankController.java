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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01234012340", "Lene", "Jensen",
                "Majorstuen 88", "1234", "Bjølsen", "12341234",
                "123456789");

        when(sjekk.loggetInn()).thenReturn("43214312431");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

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
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("123400001234", "43214312431",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("123400001234", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("43214312431");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_ikkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto resultat = bankController.hentTransaksjoner("12346666666",
                "2001-07-26", "2001-07-29");

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_loggetInn() {
        // arrange
        String kontonummer = "12345670000";
        String fraDato = "2001-07-26";
        String tilDato = "2001-07-29";
        Konto konto = new Konto();
        when(sjekk.loggetInn()).thenReturn("12345678910");
        when(repository.hentTransaksjoner(kontonummer, fraDato, tilDato)).thenReturn(konto);

        // act
        Konto resultat = bankController.hentTransaksjoner(kontonummer, fraDato, tilDato);

        // assert
        assertEquals(konto, resultat);
    }

    @Test
    public void hentSaldi_loggetInn() {
        // arrange
        List<Konto> saldo = new ArrayList<>();
        Konto konto3 = new Konto("123412341234", "123400001441",
                8989, "Lønnskonto", "NOK", null);
        saldo.add(konto3);

        when(sjekk.loggetInn()).thenReturn("123412341234");
        when(repository.hentSaldi(anyString())).thenReturn(saldo);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(saldo, resultat);
    }

    @Test
    public void hentSaldi_ikkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }

    @Test
    public void registerBetaling_loggetInn() {
        // arrange
        List<Transaksjon> transaksjon = new ArrayList<>();
        Transaksjon enTransaksjon = new Transaksjon(4, "123400001551",
                9876.54321, "2012-02-18", "Husleie", "123400001234",
                "1");
        transaksjon.add(enTransaksjon);
        Konto konto3 = new Konto("123412341234", "123400001441",
                8989, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(konto3.getPersonnummer());
        when(repository.registrerBetaling(enTransaksjon)).thenReturn("OK");

        // act
        String resultat = bankController.registrerBetaling(enTransaksjon);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registerBetaling_ikkeLoggetInn() {
        // arrange
        Transaksjon enTransaksjon = new Transaksjon(4, "123400001551",
                1234.56789, "2019-12-11", "Husleie", "123400001234",
                "1");
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String transaksjon = bankController.registrerBetaling(enTransaksjon);

        // assert
        assertNull(transaksjon);
    }

    @Test
    public void hentBetaling_loggetInn() {
        // arrange
        List<Transaksjon> transaksjon = new ArrayList<>();
        Transaksjon enTransaksjon = new Transaksjon(4, "123400001551",
                4005.75, "2028-07-12", "Husleie", "123400001234",
                "1");
        transaksjon.add(enTransaksjon);

        Konto konto3 = new Konto("123412341234", "123400001441",
                789, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("123412341234");
        when(repository.hentBetalinger(konto3.getPersonnummer())).thenReturn(transaksjon);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertEquals(transaksjon, resultat);
    }

    @Test
    public void hentBetaling_ikkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);
    }

    @Test
    public void utforBetaling_loggetInn() {
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon = new Transaksjon(4, "123400001551",
                5000.5, "2014-01-20", "Husleie", "123400001234",
                "1");
        transaksjoner.add(transaksjon);

        Konto konto3 = new Konto("123412341234", "123400001441",
                840, "Lønnskonto", "NOK", null);
        konto3.setTransaksjoner(transaksjoner);

        when(sjekk.loggetInn()).thenReturn("123412341234");
        when(repository.utforBetaling(transaksjon.getTxID())).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(transaksjon.getTxID());

        // assert
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void utforBetaling_ikkeLoggetInn() {
        // arrange
        List<Transaksjon> transaksjon = new ArrayList<>();
        Transaksjon enTransaksjon = new Transaksjon(4, "01234012342",
                3000.78, "2012-02-22", "Husleie", "01234012343",
                "1");
        transaksjon.add(enTransaksjon);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(enTransaksjon.getTxID());

        // assert
        assertNull(resultat);
    }

    @Test
    public void endre_loggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01234012340", "Lene", "Jensen",
                "Majorstuen 88", "1234", "Bjølsen", "12341234",
                "123456789");

        when(sjekk.loggetInn()).thenReturn("01234012340");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn(enKunde.getPersonnummer());

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertEquals(enKunde.getPersonnummer(), resultat);
    }

    @Test
    public void endre_ikkeLoggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01234012340", "Lene", "Jensen",
                "Majorstuen 88", "1234", "Bjølsen", "12341234",
                "123456789");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = bankController.endre(enKunde);

        // assert
        assertNull(resultat);
    }
}

