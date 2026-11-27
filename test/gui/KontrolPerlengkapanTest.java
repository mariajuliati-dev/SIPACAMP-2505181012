package gui;
import basisdata.PerlengkapanDAO;
import eksepsi.InputCampingTidakValidException;
import eksepsi.KesalahanPenyimpananException;
import entity.AlatCamping;
import entity.PeralatanMasak;
import entity.Tenda;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Maria
 */
class KontrolPerlengkapanTest {

    private static class StubPerlengkapanDAO implements PerlengkapanDAO {
        private final List<AlatCamping> data = new ArrayList<>();
        
        // Dibantu oleh AI untuk melengkapi method cariNama pada StubPerlengkapanDAO sesuai kontrak PerlengkapanDAO.
        @Override
    public List<AlatCamping> cariNama(String namaAlat) throws KesalahanPenyimpananException {
        List<AlatCamping> hasil = new ArrayList<>();
        if (namaAlat == null || namaAlat.isBlank()) {
            return hasil;
        }
        String kunci = namaAlat.trim().toLowerCase();
            for (AlatCamping item : data) {
            if (item.getNamaAlat() != null && item.getNamaAlat().toLowerCase().contains(kunci)) {
                hasil.add(item);
            }
        }
        return hasil;
    }
        @Override
        public List<AlatCamping> semua() throws KesalahanPenyimpananException {
            return new ArrayList<>(data);
        }

        @Override
        public AlatCamping cariKode(String kodeAlat) throws KesalahanPenyimpananException {
            return data.stream()
                .filter(item -> item.getKodeAlat() != null && item.getKodeAlat().equalsIgnoreCase(kodeAlat))
                .findFirst()
                .orElse(null);
        }

        @Override
        public void tambah(AlatCamping alat) throws KesalahanPenyimpananException {
            data.add(alat);
        }

        @Override
        public void ubah(AlatCamping alat) throws KesalahanPenyimpananException {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getKodeAlat().equalsIgnoreCase(alat.getKodeAlat())) {
                    data.set(i, alat);
                    return;
                }
            }
        }

        @Override
        public void hapus(String kodeAlat) throws KesalahanPenyimpananException {
            data.removeIf(item -> item.getKodeAlat() != null && item.getKodeAlat().equalsIgnoreCase(kodeAlat));
        }

        @Override
        public String sumberData() {
            return "Stub Uji Memori";
        }
    }

    @Test
    @DisplayName("Daftar perlengkapan terurut berdasarkan harga sewa termurah lalu stok")
    void daftarTerurutHargaLaluStok() throws Exception {
        KontrolPerlengkapan kontrol = new KontrolPerlengkapan(new StubPerlengkapanDAO());
        kontrol.tambahPerlengkapan(new Tenda("TND-10", "Tenda Besar", 90000, 2, "Tersedia", 6, true));
        kontrol.tambahPerlengkapan(new PeralatanMasak("MSK-10", "Kompor Portable", 25000, 6, "Tersedia", "Stainless", true));
        kontrol.tambahPerlengkapan(new PeralatanMasak("MSK-11", "Nesting Set", 25000, 2, "Tersedia", "Aluminum", true));

        List<AlatCamping> hasil = kontrol.daftarTerurut();
        assertEquals("MSK-11", hasil.get(0).getKodeAlat());
        assertEquals("MSK-10", hasil.get(1).getKodeAlat());
        assertEquals("TND-10", hasil.get(2).getKodeAlat());
    }

    @Test
    @DisplayName("Keadaan gagal: harga sewa nol atau negatif melempar InputCampingTidakValidException")
    void gagalTambahHargaTidakValid() {
        KontrolPerlengkapan kontrol = new KontrolPerlengkapan(new StubPerlengkapanDAO());
        Tenda tidakValid = new Tenda("TND-99", "Tenda Salah Harga", 0, 2, "Tersedia", 4, true);

        assertThrows(
            InputCampingTidakValidException.class,
            () -> kontrol.tambahPerlengkapan(tidakValid)
        );
    }
}