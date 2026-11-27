//package entity;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
///**
// *
// * @author Maria
// */
//public class Persewaan {
//    private String idPersewaan;
//    private LocalDate tanggalSewa;
//    private LocalDate tanggalKembali;
//    private List<DetailPersewaan> daftarDetail;
//
//    public Persewaan(String idPersewaan, LocalDate tanggalSewa, LocalDate tanggalKembali) {
//        this.idPersewaan = idPersewaan;
//        this.tanggalSewa = tanggalSewa;
//        this.tanggalKembali = tanggalKembali;
//        this.daftarDetail = new ArrayList<>();
//    }
//    
//    // Menambahkan item alat ke dalam daftar detail transaksi persewaan
//    public void tambahDetail(AlatCamping alat, int jumlah) {
//        DetailPersewaan detail = new DetailPersewaan(jumlah, alat);
//        daftarDetail.add(detail);
//    }
//
//    // Menghitung akumulasi total keseluruhan biaya sewa dari semua item alat
//    public double hitungTotalBiaya() {
//        double total = 0;
//        for (DetailPersewaan detail : daftarDetail) {
//            total += detail.getSubTotal();
//        }
//        return total;
//    }
//}

package entity;

import eksepsi.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Maria
 */
public class Persewaan {
    private String idPersewaan;
    private LocalDate tanggalSewa;
    private LocalDate tanggalKembali;
    private List<DetailPersewaan> daftarDetail;

    public Persewaan(String idPersewaan, LocalDate tanggalSewa, LocalDate tanggalKembali)
            throws PeriodeSewaTidakValidException {
        if (tanggalSewa == null || tanggalKembali == null) {
            throw new PeriodeSewaTidakValidException(
                    "Tanggal sewa dan tanggal kembali wajib diisi terlebih dahulu.");
        }
        if (tanggalKembali.isBefore(tanggalSewa)) {
            throw new PeriodeSewaTidakValidException(tanggalSewa, tanggalKembali);
        }

        this.idPersewaan = idPersewaan;
        this.tanggalSewa = tanggalSewa;
        this.tanggalKembali = tanggalKembali;
        this.daftarDetail = new ArrayList<>();
    }

    // Menambahkan item alat ke dalam daftar detail setelah validasi lolos
    public void tambahDetail(AlatCamping alat, int jumlah)
            throws AlatTidakSiapSewaException, StokSewaKurangException {
        DetailPersewaan detail = new DetailPersewaan(jumlah, alat);
        daftarDetail.add(detail);
    }

    // Menghitung lama hari sewa (minimal 1 hari jika diambil dan dikembalikan di hari yang sama)
    public long hitungDurasiHari() {
        long selisihHari = ChronoUnit.DAYS.between(tanggalSewa, tanggalKembali);
        return Math.max(1L, selisihHari);
    }

    // Menghitung akumulasi total biaya sewa seluruh item dikalikan durasi hari
    public double hitungTotalBiaya() {
        double totalPerHari = 0;
        for (DetailPersewaan detail : daftarDetail) {
            totalPerHari += detail.getSubTotal();
        }
        return totalPerHari * hitungDurasiHari();
    }

    public String getIdPersewaan() {
        return idPersewaan;
    }

    public LocalDate getTanggalSewa() {
        return tanggalSewa;
    }

    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    public List<DetailPersewaan> getDaftarDetail() {
        return Collections.unmodifiableList(daftarDetail);
    }
}