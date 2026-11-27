package entity;

import eksepsi.AlatTidakSiapSewaException;
import eksepsi.PeriodeSewaTidakValidException;
import eksepsi.StokSewaKurangException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Dibantu oleh AI untuk menyusun kasus uji nilai batas dan keadaan gagal pada entitas SIPACAMP.
class TendaDanPersewaanTest {

    @Test
    @DisplayName("Stok 0 tidak siap disewakan (batas bawah stok)")
    void stokNolTidakSiap() {
        Tenda tenda = new Tenda("TND-01", "Tenda Dome", 50000, 0, "Tersedia", 4, true);
        assertFalse(tenda.siapDisewakan());
    }

    @Test
    @DisplayName("Stok 1 dengan pasak lengkap siap disewakan (tepat batas minimal stok)")
    void stokSatuSiapDisewakan() {
        Tenda tenda = new Tenda("TND-02", "Tenda Dome", 50000, 1, "Tersedia", 4, true);
        assertTrue(tenda.siapDisewakan());
    }

    @Test
    @DisplayName("Durasi sewa di hari yang sama minimal bernilai 1 hari (batas durasi)")
    void durasiMinimalSatuHari() throws Exception {
        LocalDate hariIni = LocalDate.now();
        Persewaan p = new Persewaan("SRW-101", hariIni, hariIni);
        assertEquals(1L, p.hitungDurasiHari());
    }

    @Test
    @DisplayName("Peminjaman lewat 5 hari sejak tanggal kembali terhitung terlambat 5 hari")
    void keterlambatanLimaHari() throws Exception {
        Persewaan p = new Persewaan(
            "SRW-102",
            LocalDate.now().minusDays(10),
            LocalDate.now().minusDays(5)
        );
        assertEquals(5L, p.hitungHariTerlambat());
    }

    @Test
    @DisplayName("Keadaan gagal: jumlah sewa melebihi stok melempar StokSewaKurangException")
    void gagalSewaMelebihiStok() throws Exception {
        Tenda tenda = new Tenda("TND-03", "Tenda Ultralight", 60000, 2, "Tersedia", 2, true);
        Persewaan p = new Persewaan("SRW-103", LocalDate.now(), LocalDate.now().plusDays(2));
        assertThrows(StokSewaKurangException.class, () -> p.tambahDetail(tenda, 3));
    }

    @Test
    @DisplayName("Keadaan gagal: tanggal kembali sebelum tanggal sewa melempar PeriodeSewaTidakValidException")
    void gagalTanggalKembaliLebihAwal() {
        assertThrows(
            PeriodeSewaTidakValidException.class,
            () -> new Persewaan("SRW-104", LocalDate.now().plusDays(3), LocalDate.now())
        );
    }

    @Test
    @DisplayName("Keadaan gagal: tenda tanpa pasak lengkap ditolak saat ditambahkan ke persewaan")
    void gagalSewaTendaPasakTidakLengkap() throws Exception {
        Tenda tendaRusak = new Tenda("TND-04", "Tenda Basecamp", 80000, 3, "Tersedia", 6, false);
        Persewaan p = new Persewaan("SRW-105", LocalDate.now(), LocalDate.now().plusDays(2));
        assertThrows(AlatTidakSiapSewaException.class, () -> p.tambahDetail(tendaRusak, 1));
    }
}
