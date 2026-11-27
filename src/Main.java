import aturan.*;
import basisdata.*;
import berkas.*;
import eksepsi.*;
import entity.*;
import java.time.LocalDate;
import java.util.List;
import paket.*;
/**
 *
 * @author Maria
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Sistem Persewaan Alat Camping (SIPACAMP)\n");

        // 1. Inisialisasi Objek (Pewarisan & Abstract Class)
        Tenda tendaCamping = new Tenda("TND-001", "Tenda Dome 4 Orang", 75000, 3, "Tersedia", 4, true);
        PeralatanMasak alatMasak = new PeralatanMasak("MSK-001", "Nesting Cooking Set", 25000, 5, "Tersedia", "Aluminum Anodized", true);
        Tenda tendaKecil = new Tenda("TND-002", "Tenda Ultralight 2 Orang", 50000, 6, "Tersedia", 2, true);
        Tenda tendaRusakPasak = new Tenda("TND-003", "Tenda Basecamp 6 Orang", 90000, 2, "Tersedia", 6, false);

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
        kelola.tambahAlat(tendaKecil);
        kelola.tambahAlat(tendaRusakPasak);

        for (AlatCamping alat : kelola.getKatalogAlat()) {
            System.out.println("- " + alat.getNamaAlat() + " | Tarif: Rp " + alat.getHargaSewaPerHari()
                    + "/hari | Perawatan: Rp " + alat.hitungBiayaPerawatan());
        }

        // 3. Pengujian Relasi Transaksi (Persewaan & DetailPersewaan) & Penanganan Eksepsi
        System.out.println("\n--- Uji Relasi Transaksi & Eksepsi ---");
        prosesTransaksi("SRW-001", LocalDate.now(), LocalDate.now().plusDays(3), tendaCamping, 1, alatMasak, 2);

        // Uji kesalahan: tanggal kembali lebih awal dari tanggal sewa
        prosesTransaksi("SRW-002", LocalDate.now().plusDays(3), LocalDate.now(), tendaCamping, 1, alatMasak, 1);

        // Uji kesalahan: jumlah melebihi stok
        prosesTransaksi("SRW-003", LocalDate.now(), LocalDate.now().plusDays(2), tendaCamping, 99, alatMasak, 1);

        // Uji kesalahan: alat belum siap disewakan (pasak tidak lengkap)
        prosesTransaksi("SRW-004", LocalDate.now(), LocalDate.now().plusDays(2), tendaRusakPasak, 1, alatMasak, 1);

        // Uji validasi input angka hari (termasuk input huruf)
        ujiInputDurasi("3");
        ujiInputDurasi("tiga hari");
        ujiInputDurasi("0");

        // 4. Pengujian Generic, Collection (Map), Comparator & Lambda
        System.out.println("\n--- Uji Kelas Generic PaketCamping ---");
        PaketCamping<AlatCamping> paketHemat = new PaketCamping<>("Paket Camping Ceria", 3);
        try {
            paketHemat.masukkanAlat(tendaKecil);
            paketHemat.masukkanAlat(alatMasak);
        } catch (SipacampException ex) {
            System.out.println("Gagal memasukkan alat ke paket: " + ex.getMessage());
        }

        System.out.println("Nama Paket  : " + paketHemat.getNamaPaket());
        for (AlatCamping item : paketHemat) {
            System.out.println("  * " + item.getNamaAlat() + " (Rp " + item.getHargaSewaPerHari() + "/hari)");
        }
        System.out.println("Total Tarif : Rp " + paketHemat.hitungTotalTarifPaket() + "/hari");

        System.out.println("\n--- Uji Pencarian Cepat dengan Map ---");
        AlatCamping hasilCari = kelola.cariByKode("MSK-001");
        if (hasilCari != null) {
            System.out.println("Ditemukan Kode MSK-001: " + hasilCari.getNamaAlat() + " (Stok: " + hasilCari.getStok() + ")");
        }

        System.out.println("\n--- Uji Pengurutan (Comparator Harga Termurah lalu Stok) ---");
        for (AlatCamping a : kelola.urutkanTarifTermurah()) {
            System.out.println("- " + a.getNamaAlat() + " | Rp " + a.getHargaSewaPerHari() + " | Stok: " + a.getStok());
        }

        System.out.println("\n--- Uji Penyaringan Lambda (Tarif <= Rp 50.000) ---");
        for (AlatCamping a : kelola.cariTarifMaksimal(50000)) {
            System.out.println("- " + a.getNamaAlat() + " (Rp " + a.getHargaSewaPerHari() + ")");
        }

        // 5. Pengujian Berkas Teks/CSV, Serialisasi Objek & Basis Data MySQL (DAO)
        System.out.println("\n--- Uji Laporan Teks/CSV, Serialisasi & DAO MySQL ---");
        try {
            LaporanCamping laporan = new LaporanCamping();
            laporan.tulisRingkasanTeks("laporan_perlengkapan_sipacamp.txt", kelola.getKatalogAlat());
            laporan.tulisCsv("perlengkapan_sipacamp.csv", kelola.getKatalogAlat());
            System.out.println("Berkas laporan teks dan CSV berhasil ditulis.");

            ArsipSerialCamping arsip = new ArsipSerialCamping("arsip_perlengkapan_sipacamp.dat");
            arsip.simpanDaftar(kelola.getKatalogAlat());
            List<AlatCamping> daftarDimuat = arsip.muatDaftar();
            System.out.println("Serialisasi berhasil. Jumlah item termuat: " + daftarDimuat.size());

            KoneksiSipacamp koneksiA = KoneksiSipacamp.getInstance();
            KoneksiSipacamp koneksiB = KoneksiSipacamp.getInstance();
            System.out.println("Instance Singleton sama? " + (koneksiA == koneksiB));

            if (koneksiA.koneksiAktif()) {
                PerlengkapanDAO dao = new PerlengkapanDAOMySQL();
                System.out.println("Sumber data aktif: " + dao.sumberData());

                dao.hapus("TND-909");

                AlatCamping itemUji = new Tenda("TND-909", "Tenda Uji Sibolangit", 60000, 4, "Tersedia", 4, true);
                dao.tambah(itemUji);
                System.out.println("Jumlah data di DB setelah tambah: " + dao.semua().size());

                AlatCamping cariUji = dao.cariKode("TND-909");
                if (cariUji != null) {
                    System.out.println("Cari kode TND-909: " + cariUji.getNamaAlat());
                }

                AlatCamping ubahUji = new Tenda("TND-909", "Tenda Uji Sibolangit (Revisi)", 65000, 3, "Tersedia", 4, true);
                dao.ubah(ubahUji);
                System.out.println("Data TND-909 berhasil diperbarui.");

                dao.hapus("TND-909");
                System.out.println("Data TND-909 dihapus. Jumlah data di DB sekarang: " + dao.semua().size());
            } else {
                System.out.println("Koneksi MySQL belum aktif. Jalankan MySQL & cek konfigurasi KoneksiSipacamp.");
            }
        } catch (SipacampException ex) {
            System.out.println("Kesalahan penyimpanan/basis data: " + ex.getMessage());
        }

        // 6. Pengujian Design Pattern Strategy pada Aturan Denda Persewaan
        System.out.println("\n--- Uji Design Pattern Strategy (Aturan Denda) ---");
        try {
            Persewaan sewaTerlambat = new Persewaan(
                    "SRW-901",
                    LocalDate.now().minusDays(10),
                    LocalDate.now().minusDays(5),
                    "Budi",
                    "Petugas Posko"
            );
            sewaTerlambat.tambahDetail(tendaCamping, 1);
            System.out.println("Hari terlambat: " + sewaTerlambat.hitungHariTerlambat() + " hari");

            List<AturanDendaCamping> daftarAturan = List.of(
                    new DendaHarianCamping(),
                    new DendaBertingkatCamping()
            );
            for (AturanDendaCamping aturan : daftarAturan) {
                System.out.printf("%-42s -> Denda: Rp %d%n",
                        aturan.namaAturan(), sewaTerlambat.hitungDenda(aturan));
            }
        } catch (SipacampException ex) {
            System.out.println("Gagal menguji aturan denda: " + ex.getMessage());
        }

        System.out.println("\nProgram selesai tanpa berhenti mendadak.");
    }

    private static void prosesTransaksi(String idTransaksi, LocalDate tglSewa, LocalDate tglKembali,
            AlatCamping alatUtama, int jumlahUtama, AlatCamping alatTambahan, int jumlahTambahan) {
        try {
            Persewaan transaksi = new Persewaan(idTransaksi, tglSewa, tglKembali);
            transaksi.tambahDetail(alatUtama, jumlahUtama);
            transaksi.tambahDetail(alatTambahan, jumlahTambahan);
            System.out.println("[" + idTransaksi + "] Berhasil | Durasi: " + transaksi.hitungDurasiHari()
                    + " hari | Total Biaya Sewa: Rp " + transaksi.hitungTotalBiaya());
        } catch (SipacampException ex) {
            System.out.println("[" + idTransaksi + "] Ditolak: " + ex.getMessage());
        }
    }

    private static void ujiInputDurasi(String inputHari) {
        try {
            int hari = Integer.parseInt(inputHari.trim());
            if (hari <= 0) {
                throw new InputCampingTidakValidException("Durasi Sewa",
                        "Durasi sewa harus minimal 1 hari. Masukkan angka bulat lebih dari 0.");
            }
            System.out.println("Durasi \"" + inputHari + "\" valid: " + hari + " hari.");
        } catch (NumberFormatException ex) {
            System.out.println("Masukan durasi \"" + inputHari
                    + "\" tidak valid. Harap masukkan angka bulat, contoh: 2.");
        } catch (InputCampingTidakValidException ex) {
            System.out.println("Validasi gagal (" + ex.getNamaKolom() + "): " + ex.getMessage());
        }
    }
}