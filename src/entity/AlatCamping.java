package entity;
import java.io.Serializable;
/**
 * Kelas induk abstrak untuk perlengkapan camping pada SIPACAMP.
 * @author Maria
 */
public abstract class AlatCamping implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String kodeAlat;
    protected String namaAlat;
    protected double hargaSewaPerHari;
    protected int stok;
    protected String status;

    public AlatCamping(String kodeAlat, String namaAlat, double hargaSewaPerHari, int stok, String status) {
        this.kodeAlat = kodeAlat;
        this.namaAlat = namaAlat;
        this.hargaSewaPerHari = hargaSewaPerHari;
        this.stok = stok;
        this.status = status;
    }
    
    public String laporanRingkas() {
        String kesiapan = siapDisewakan() ? "SIAP" : "TIDAK SIAP";
        return String.format("%-10s %-26s %-12s", kodeAlat, namaAlat, kesiapan);
    }

    public String deskripsi() {
        return kodeAlat + " - " + namaAlat + " (Rp" + hargaSewaPerHari + "/hari, stok: " + stok + ")";
    }

    public abstract double hitungBiayaPerawatan();

    public abstract boolean siapDisewakan();

    public String getKodeAlat() {
        return kodeAlat;
    }

    public String getNamaAlat() {
        return namaAlat;
    }

    public double getHargaSewaPerHari() {
        return hargaSewaPerHari;
    }

    public int getStok() {
        return stok;
    }

    public String getStatus() {
        return status;
    }
}