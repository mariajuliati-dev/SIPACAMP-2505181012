package entity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Maria
 */
public class KelolaAlat {
    private List<AlatCamping> katalogAlat = new ArrayList<>();
    private Map<String, AlatCamping> petaKatalog = new HashMap<>();

    public void tambahAlat(AlatCamping alat) {
        katalogAlat.add(alat);
        petaKatalog.put(alat.kodeAlat, alat);
    }

    public List<AlatCamping> getKatalogAlat() {
        return katalogAlat;
    }
    
    // Memproses koleksi bertipe induk (AlatCamping) dan memanggil method override
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

    // Sorting tarif termurah menggunakan Lambda Expression
    public void urutkanTarifTermurah() {
        katalogAlat.sort(Comparator.comparingDouble(a -> a.hargaSewaPerHari));
    }
}
