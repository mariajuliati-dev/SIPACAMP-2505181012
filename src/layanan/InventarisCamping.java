package layanan;
import entity.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Maria
 */
public class InventarisCamping {

    private final List<AlatCamping> daftarAlat = new ArrayList<>();

    public void tambah(AlatCamping alat) {
        daftarAlat.add(alat);
    }

    public List<AlatCamping> semuaAlat() {
        return daftarAlat;
    }

    public void cetakStatus() {
        for (AlatCamping alat : daftarAlat) {
            System.out.println(alat.laporanRingkas());
        }
    }

    public int hitungSiapDisewakan() {
        int jumlah = 0;
        for (AlatCamping alat : daftarAlat) {
            if (alat.siapDisewakan()) {
                jumlah++;
            }
        }
        return jumlah;
    }

    public List<AlatCamping> daftarTidakSiap() {
        List<AlatCamping> hasil = new ArrayList<>();
        for (AlatCamping alat : daftarAlat) {
            if (!alat.siapDisewakan()) {
                hasil.add(alat);
            }
        }
        return hasil;
    }

    public void cetakStatusPemeriksaan() {
        for (AlatCamping alat : daftarAlat) {
            if (alat instanceof TerperiksaBerkala itemTerperiksa) {
                System.out.println(alat.getNamaAlat() + " -> " + itemTerperiksa.statusPemeriksaan());
            }
        }
    }
}
