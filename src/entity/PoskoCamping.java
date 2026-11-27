package entity;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Maria
 */
public class PoskoCamping {

    private final String kodePosko;
    private final String namaPosko;

    // AGREGASI: perlengkapan dibuat di luar dan hanya ditampung oleh posko
    private final List<AlatCamping> koleksiPerlengkapan = new ArrayList<>();

    public PoskoCamping(String kodePosko, String namaPosko) {
        this.kodePosko = kodePosko;
        this.namaPosko = namaPosko;
    }

    public void tambahPerlengkapan(AlatCamping alat) {
        koleksiPerlengkapan.add(alat);
    }

    public int jumlahPerlengkapan() {
        return koleksiPerlengkapan.size();
    }

    public String getKodePosko() {
        return kodePosko;
    }

    public String getNamaPosko() {
        return namaPosko;
    }
}
