package entity;
import aturan.AturanDendaCamping;
import aturan.DendaBertingkatCamping;
import aturan.DendaHarianCamping;
import eksepsi.AlatTidakSiapSewaException;
import eksepsi.PeriodeSewaTidakValidException;
import eksepsi.StokSewaKurangException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Maria
 */
class PersewaanTest {

    private Tenda buatTendaSiap(String kode, int stok) {
        return new Tenda(kode, "Tenda Uji Camping", 60000, stok, "Tersedia", 4, true);
    }

    @Test
    @DisplayName("Sewa dan kembali di hari yang sama minimal bernilai 1 hari (batas durasi)")
    void durasiMinimalSatuHari() throws Exception {
        LocalDate hariIni = LocalDate.now();
        Persewaan persewaan = new Persewaan("SRW-201", hariIni, hariIni);
        assertEquals(1L, persewaan.hitungDurasiHari());
    }

    @Test
    @DisplayName("Terlambat tepat 3 hari dihitung benar oleh DendaHarianCamping dan DendaBertingkatCamping")
    void dendaTerlambatTigaHari() throws Exception {
        Persewaan persewaan = new Persewaan(
            "SRW-202",
            LocalDate.now().minusDays(6),
            LocalDate.now().minusDays(3)
        );
        AturanDendaCamping harian = new DendaHarianCamping();
        AturanDendaCamping bertingkat = new DendaBertingkatCamping();

        assertEquals(3L, persewaan.hitungHariTerlambat());
        assertEquals(45000L, persewaan.hitungDenda(harian));
        assertEquals(30000L, persewaan.hitungDenda(bertingkat));
    }

    @Test
    @DisplayName("Keadaan gagal: tanggal kembali sebelum tanggal sewa melempar PeriodeSewaTidakValidException")
    void gagalTanggalKembaliLebihAwal() {
        assertThrows(
            PeriodeSewaTidakValidException.class,
            () -> new Persewaan("SRW-203", LocalDate.now().plusDays(2), LocalDate.now())
        );
    }

    @Test
    @DisplayName("Keadaan gagal: jumlah melebihi stok melempar StokSewaKurangException")
    void gagalJumlahMelebihiStok() throws Exception {
        Tenda tenda = buatTendaSiap("TND-204", 2);
        Persewaan persewaan = new Persewaan("SRW-204", LocalDate.now(), LocalDate.now().plusDays(2));
        assertThrows(StokSewaKurangException.class, () -> persewaan.tambahDetail(tenda, 3));
    }

    @Test
    @DisplayName("Keadaan gagal: tenda tanpa pasak lengkap ditolak dengan AlatTidakSiapSewaException")
    void gagalTendaPasakTidakLengkap() throws Exception {
        Tenda tendaTanpaPasak = new Tenda("TND-205", "Tenda Rusak Pasak", 75000, 3, "Tersedia", 4, false);
        Persewaan persewaan = new Persewaan("SRW-205", LocalDate.now(), LocalDate.now().plusDays(2));
        assertThrows(AlatTidakSiapSewaException.class, () -> persewaan.tambahDetail(tendaTanpaPasak, 1));
    }
}
