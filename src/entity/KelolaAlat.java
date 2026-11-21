package entity;

import aturanurut.UrutHargaLaluStok;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dibantu oleh AI untuk melengkapi pengelolaan koleksi Map, pengurutan salinan
 * dengan Comparator berlapis, serta penyaringan data aman.
 *
 * @author Maria
 */
public class KelolaAlat {
    private final List<AlatCamping> katalogAlat = new ArrayList<>();
    private final Map<String, AlatCamping> petaKatalog = new HashMap<>();

    public void tambahAlat(AlatCamping alat) {
        katalogAlat.add(alat);
        petaKatalog.put(alat.kodeAlat, alat);
    }

    public List<AlatCamping> getKatalogAlat() {
        return katalogAlat;
    }

    // Pencarian cepat O(1) memanfaatkan Map berdasarkan kodeAlat
    public AlatCamping cariByKode(String kodeAlat) {
        return petaKatalog.get(kodeAlat);
    }

    // Memproses koleksi bertipe induk (AlatCamping) secara polimorfik (Poin 2)
    public void tampilkanKatalogPolimorfik() {
        System.out.println("=== LAPORAN KATALOG & PERAWATAN ALAT (POLIMORFISME) ===");
        for (AlatCamping alat : katalogAlat) {
            System.out.println("- Nama Alat       : " + alat.getNamaAlat());
            System.out.println("  Tarif Sewa/Hari : Rp " + alat.hargaSewaPerHari);
            System.out.println("  Biaya Perawatan : Rp " + alat.hitungBiayaPerawatan());
            System.out.println("  Status Siap Sewa: " + alat.siapDisewakan());
            System.out.println("--------------------------------------------------");
        }
    }

    // Mengurutkan pada salinan List agar urutan asli katalogAlat tidak berubah
    public List<AlatCamping> urutkanKatalog(Comparator<AlatCamping> aturanUrut) {
        List<AlatCamping> salinan = new ArrayList<>(katalogAlat);
        salinan.sort(aturanUrut);
        return salinan;
    }

    // Menggunakan Comparator berlapis (harga termurah lalu stok terbanyak)
    public List<AlatCamping> urutkanTarifTermurah() {
        return urutkanKatalog(new UrutHargaLaluStok());
    }

    // Penyaringan data memakai lambda (tanpa mengubah daftar asli)
    public List<AlatCamping> cariTarifMaksimal(double batasHarga) {
        List<AlatCamping> hasil = new ArrayList<>(katalogAlat);
        hasil.removeIf(alat -> alat.hargaSewaPerHari > batasHarga);
        return hasil;
    }

    // Menghapus item yang tidak siap disewakan secara aman (bebas ConcurrentModificationException)
    public void bersihkanAlatTidakSiap() {
        katalogAlat.removeIf(alat -> !alat.siapDisewakan());
        petaKatalog.values().removeIf(alat -> !alat.siapDisewakan());
    }
}