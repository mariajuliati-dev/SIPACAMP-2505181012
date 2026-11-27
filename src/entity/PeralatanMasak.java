package entity;
import java.io.Serializable;
/**
 *
 * @author Maria
 */
public class PeralatanMasak extends AlatCamping implements InfoKelayakan, Serializable {
    private static final long serialVersionUID = 1L;
    private String bahanMaterial;
    private boolean kebersihanTerjaga;

    public PeralatanMasak(String kodeAlat, String namaAlat, double hargaSewaPerHari, int stok, String status, String bahanMaterial, boolean kebersihanTerjaga) {
        super(kodeAlat, namaAlat, hargaSewaPerHari, stok, status);
        this.bahanMaterial = bahanMaterial;
        this.kebersihanTerjaga = kebersihanTerjaga;
    }

    //Menghitung biaya perawatan tetap untuk peralatan masak
    @Override
    public double hitungBiayaPerawatan() {
        return 3000;
    }

    //Memeriksa kesiapan sewa peralatan masak berdasarkan kebersihan dan stok
    @Override
    public boolean siapDisewakan() {
        return stok > 0 && kebersihanTerjaga && status.equalsIgnoreCase("Tersedia");
    }

    //Menampilkan detail informasi material dan kebersihan alat masak
    @Override
    public void tampilkanDetailKhusus() {
        System.out.println("Bahan Material: " + bahanMaterial + " | Kebersihan Terjaga: " + (kebersihanTerjaga ? "Ya" : "Tidak"));
    }

    //Mengembalikan status kelayakan pakai peralatan masak
    @Override
    public boolean cekKondisiSiapPakai() {
        return siapDisewakan();
    }
    
    public String getBahanMaterial() {
        return bahanMaterial;
    }

    public boolean isKebersihanTerjaga() {
        return kebersihanTerjaga;
    }
}
