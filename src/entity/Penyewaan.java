package entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Maria
 */
public class Penyewaan {
    private String idPenyewaan;
    private LocalDate tanggalSewa;
    private LocalDate tanggalKembali;
    private List<DetailPenyewaan> daftarDetail;

    public Penyewaan(String idPenyewaan, LocalDate tanggalSewa, LocalDate tanggalKembali) {
        this.idPenyewaan = idPenyewaan;
        this.tanggalSewa = tanggalSewa;
        this.tanggalKembali = tanggalKembali;
        this.daftarDetail = new ArrayList<>();
    }
    //Menambahkan item alat ke dalam daftar detail transaksi penyewaan
    public void tambahDetail(AlatCamping alat, int jumlah) {
        DetailPenyewaan detail = new DetailPenyewaan(jumlah, alat);
        daftarDetail.add(detail);
    }

    //Menghitung akumulasi total keseluruhan biaya sewa dari semua item alat
    public double hitungTotalBiaya() {
        double total = 0;
        for (DetailPenyewaan detail : daftarDetail) {
            total += detail.getSubTotal();
        }
        return total;
    }
    }
