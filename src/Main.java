import entity.*;
import java.time.LocalDate;

/**
 *
 * @author Maria
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Sistem Persewaan Alat Camping (SIPACAMP)\n");

        // 1. Inisialisasi Objek (Pewarisan & Abstract Class
        Tenda tendaCamping = new Tenda("TND-001", "Tenda Dome 4 Orang", 75000, 3, "Tersedia", 4, true);
        PeralatanMasak alatMasak = new PeralatanMasak("MSK-001", "Nesting Cooking Set", 25000, 5, "Tersedia", "Aluminum Anodized", true);

        System.out.println("--- Uji Abstract Class & Interface ---");
        System.out.println("Nama Alat       : " + tendaCamping.getNamaAlat());
        System.out.println("Harga Sewa/Hari : Rp " + tendaCamping.getHargaSewaPerHari());
        System.out.println("Biaya Perawatan : Rp " + tendaCamping.hitungBiayaPerawatan());
        System.out.println("Status Siap Sewa: " + tendaCamping.siapDisewakan());
        System.out.print("Detail Khusus   : ");
        tendaCamping.tampilkanDetailKhusus();

        // 2. Pengujian Polimorfisme & Koleksi 
        System.out.println("\n--- Uji Polimorfisme & Koleksi ---");
        KelolaAlat kelola = new KelolaAlat();
        kelola.tambahAlat(tendaCamping);
        kelola.tambahAlat(alatMasak);

        for (AlatCamping alat : kelola.getKatalogAlat()) {
            System.out.println("- " + alat.getNamaAlat() + " | Tarif: Rp " + alat.getHargaSewaPerHari() + "/hari | Perawatan: Rp " + alat.hitungBiayaPerawatan());
        }

        // 3. Pengujian Relasi Transaksi (Persewaan & DetailPersewaan - Komposisi)
        System.out.println("\n--- Uji Relasi Transaksi ---");
        Persewaan transaksi = new Persewaan("SRW-001", LocalDate.now(), LocalDate.now().plusDays(3));
        transaksi.tambahDetail(tendaCamping, 1);
        transaksi.tambahDetail(alatMasak, 2);

        System.out.println("ID Persewaan    : SRW-001");
        System.out.println("Total Biaya Sewa: Rp " + transaksi.hitungTotalBiaya());
    }
}
