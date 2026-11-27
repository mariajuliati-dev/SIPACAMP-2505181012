package entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Maria
 */
class TendaTest {

    @Test
    @DisplayName("Stok 0 tidak siap disewakan (batas bawah stok)")
    void stokNolTidakSiapDisewakan() {
        Tenda tenda = new Tenda("TND-01", "Tenda Dome 2 Orang", 50000, 0, "Tersedia", 2, true);
        assertFalse(tenda.siapDisewakan());
    }

    @Test
    @DisplayName("Stok 1 dengan pasak lengkap siap disewakan (tepat batas minimal stok)")
    void stokSatuSiapDisewakan() {
        Tenda tenda = new Tenda("TND-02", "Tenda Dome 2 Orang", 50000, 1, "Tersedia", 2, true);
        assertTrue(tenda.siapDisewakan());
    }

    @Test
    @DisplayName("Stok 5 di atas batas minimal dengan pasak lengkap siap disewakan")
    void stokLimaSiapDisewakan() {
        Tenda tenda = new Tenda("TND-03", "Tenda Basecamp 6 Orang", 90000, 5, "Tersedia", 6, true);
        assertTrue(tenda.siapDisewakan());
    }
}
