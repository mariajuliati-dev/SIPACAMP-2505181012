package entity;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public Persewaan(String idPersewaan, LocalDate tanggalSewa, LocalDate tanggalKembali) {
        this.idPersewaan = idPersewaan;
        this.tanggalSewa = tanggalSewa;
        this.tanggalKembali = tanggalKembali;
        this.daftarDetail = new ArrayList<>();
    }
    
    // Menambahkan item alat ke dalam daftar detail transaksi persewaan
    public void tambahDetail(AlatCamping alat, int jumlah) {
        DetailPersewaan detail = new DetailPersewaan(jumlah, alat);
        daftarDetail.add(detail);
    }

    // Menghitung akumulasi total keseluruhan biaya sewa dari semua item alat
    public double hitungTotalBiaya() {
        double total = 0;
        for (DetailPersewaan detail : daftarDetail) {
            total += detail.getSubTotal();
        }
        return total;
    }
}
