package entity;

import java.io.Serializable;

/**
 *
 * @author Maria
 */
public class Tenda extends AlatCamping implements InfoKelayakan, Serializable {
    private static final long serialVersionUID = 1L;
    private int kapasitasOrang;
    private boolean kondisiPasakLengkap;

    public Tenda(String kodeAlat, String namaAlat, double hargaSewaPerHari, int stok, String status, int kapasitasOrang, boolean kondisiPasakLengkap) {
        super(kodeAlat, namaAlat, hargaSewaPerHari, stok, status);
        this.kapasitasOrang = kapasitasOrang;
        this.kondisiPasakLengkap = kondisiPasakLengkap;
    }

    // Menghitung biaya perawatan tenda berdasarkan kapasitas orang
    @Override
    public double hitungBiayaPerawatan() {
        return 5000 * kapasitasOrang;
    }

    // Memeriksa kesiapan sewa tenda berdasarkan stok, pasak, dan status
    @Override
    public boolean siapDisewakan() {
        return stok > 0 && kondisiPasakLengkap && status.equalsIgnoreCase("Tersedia");
    }

    // Implementasi dari Interface InfoKelayakan
    @Override
    public void tampilkanDetailKhusus() {
        System.out.println("Kapasitas: " + kapasitasOrang + " Orang | Pasak Lengkap: " + (kondisiPasakLengkap ? "Ya" : "Tidak"));
    }

    // Mengembalikan status kelayakan pakai tenda
    @Override
    public boolean cekKondisiSiapPakai() {
        return siapDisewakan();
    }

    public int getKapasitasOrang() {
        return kapasitasOrang;
    }

    public boolean isKondisiPasakLengkap() {
        return kondisiPasakLengkap;
    }
}
