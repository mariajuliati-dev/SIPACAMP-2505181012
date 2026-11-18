package entity;

/**
 *
 * @author Maria
 */
/*
 * ATRIBUSI PENGGUNAAN AI GENERATIF:
 * Dibantu oleh AI untuk menyusun dokumentasi method dan struktur abstract class.
 */
public abstract class AlatCamping {
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
}
