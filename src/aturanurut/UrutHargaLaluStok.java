package aturanurut;
import entity.AlatCamping;
import java.util.Comparator;
/**
 *
 * @author Maria
 */

public class UrutHargaLaluStok implements Comparator<AlatCamping> {

    @Override
    public int compare(AlatCamping a, AlatCamping b) {
        // Lapis pertama: harga sewa termurah tampil lebih dulu (ascending)
        int bandingHarga = Double.compare(a.getHargaSewaPerHari(), b.getHargaSewaPerHari());
        if (bandingHarga != 0) {
            return bandingHarga;
        }
        // Lapis kedua (tie-breaker): jika harga sama, stok terbanyak tampil lebih dulu (descending)
        return Integer.compare(b.getStok(), a.getStok());
    }
}
