package entity;
import java.io.Serializable;
/**
 *
 * @author Maria
 */
/*
 * Dibantu oleh AI untuk menyusun dokumentasi method dan struktur abstract class.
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
    
    //Menghitung biaya perawatan alat secara polimorfik
    public abstract double hitungBiayaPerawatan();
    public abstract boolean siapDisewakan();
    
    //Memeriksa apakah alat siap disewakan berdasarkan kondisi tertentu
    public String getNamaAlat() {
        return namaAlat;
    }
    public double getHargaSewaPerHari() {
        return hargaSewaPerHari;
    }
    
    public int getStok() {
        return stok;
    }
    
    public String getKodeAlat() {
        return kodeAlat;
    }

    public String getStatus() {
        return status;
    }
}
