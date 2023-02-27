package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void testHentAlleKonti_LoggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();
        konti.add(new Konto());

        when(sjekk.loggetInn()).thenReturn("812751");

        when(repository.hentAlleKonti()).thenReturn(konti);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertEquals(konti, resultat);
    }
    @Test
    public void testHentAlleKonti_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void testRegistrerKonto_LoggetInn() {
        // arrange
        Konto enKonto = new Konto("125521231", "41215512", 16000, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("41215512");
        when(repository.registrerKonto(enKonto)).thenReturn("Konto registrert");

        // act
        String resultat = adminKontoController.registrerKonto(enKonto);

        // assert
        assertEquals("Konto registrert", resultat);
    }
    @Test
    public void testRegistrerKonto_IkkeLoggetInn() {
        // arrange
        Konto enKonto = new Konto("125521231", "41215512", 16000, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.registrerKonto(enKonto);

        // assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void testEndreKonto_LoggetInn() {
        // arrange
        Konto enKonto = new Konto("94412151241", "61515412", 2000, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("61515412");

        when(repository.endreKonto(enKonto)).thenReturn("Konto Endret");

        // act
        String resultat = adminKontoController.endreKonto(enKonto);

        // assert
        assertEquals("Konto Endret", resultat);
    }
    @Test
    public void testEndreKonto_IkkeLoggetInn() {
        // arrange
        Konto enKonto = new Konto("94412151241", "61515412", 2000, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.endreKonto(enKonto);

        // assert
        assertEquals("Ikke innlogget", resultat);
    }
    @Test
    public void testSlettKonto_LoggetInn() {
        // arrange
        String kontonummer = "991257812";

        when(sjekk.loggetInn()).thenReturn("991257812");
        when(repository.slettKonto(kontonummer)).thenReturn("Konto Slettet");

        // act
        String resultat = adminKontoController.slettKonto(kontonummer);

        // assert
        assertEquals("Konto Slettet", resultat);
    }
    @Test
    public void testSlettKonto_IkkeLoggetInn() {
        // arrange
        String kontonummer = "991257812";

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.slettKonto(kontonummer);

        // assert
        assertEquals("Ikke innlogget", resultat);
    }
}


