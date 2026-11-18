import entity.*;
/**
 *
 * @author Maria
 */

/*
 * Dibantu oleh AI untuk menyusun kelas pengujian Main.java guna menguji 
 * abstract class AlatCamping, turunan Tenda & PeralatanMasak, serta interface InfoKelayakan.
 */

public class Main {
    public static void main(String[] args) {
        // Membuat objek Tenda (menguji abstract class, pewarisan, override, dan interface InfoKelayakan)
        Tenda tendaCamping = new Tenda("TND-001", "Tenda Dome Camping 4 Orang", 75000, 3, "Tersedia", 4, true);
        
        // Membuat objek PeralatanMasak
        PeralatanMasak alatMasak = new PeralatanMasak("MSK-001", "Nesting Camping Set", 25000, 5, "Tersedia", "Aluminum Anodized", true);

        // Menampilkan pengujian informasi Tenda
        System.out.println("=== PENGUJIAN ALAT CAMPING SIPACAMP ===");
        System.out.println("Nama Alat: " + tendaCamping.getNamaAlat());
        System.out.println("Biaya Perawatan: Rp " + tendaCamping.hitungBiayaPerawatan());
        System.out.println("Siap Disewakan: " + tendaCamping.siapDisewakan());
        System.out.print("Detail Khusus Tenda -> ");
        tendaCamping.tampilkanDetailKhusus();
        
        System.out.println("--------------------------------------------------");

        // Menampilkan pengujian informasi Peralatan Masak
        System.out.println("Nama Alat: " + alatMasak.getNamaAlat());
        System.out.println("Biaya Perawatan: Rp " + alatMasak.hitungBiayaPerawatan());
        System.out.println("Siap Disewakan: " + alatMasak.siapDisewakan());
        System.out.print("Detail Khusus Alat Masak -> ");
        alatMasak.tampilkanDetailKhusus();
    }
}
