package entity;
import aturan.AturanDendaCamping;
import eksepsi.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Merepresentasikan transaksi persewaan perlengkapan camping pada SIPACAMP.
 *
 * @author Maria
 */
public class Persewaan {

    private String idPersewaan;
    private LocalDate tanggalSewa;
    private LocalDate tanggalKembali;

    // ASOSIASI: pihak penyewa dan petugas dirujuk dari luar
    private String namaPenyewa;
    private String namaPetugas;

    // KOMPOSISI: daftar detail dibuat di dalam Persewaan dan ikut terhapus bersamanya
    private List<DetailPersewaan> daftarDetail;

    public Persewaan(String idPersewaan, LocalDate tanggalSewa, LocalDate tanggalKembali)
            throws PeriodeSewaTidakValidException {
        this(idPersewaan, tanggalSewa, tanggalKembali, "-", "-");
    }

    public Persewaan(
            String idPersewaan,
            LocalDate tanggalSewa,
            LocalDate tanggalKembali,
            String namaPenyewa,
            String namaPetugas
    ) throws PeriodeSewaTidakValidException {
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
        this.namaPenyewa = namaPenyewa;
        this.namaPetugas = namaPetugas;
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

    public void kembalikan() {
        this.tanggalKembali = LocalDate.now();
    }

    public long hitungHariTerlambat() {
        long selisih = ChronoUnit.DAYS.between(tanggalKembali, LocalDate.now());
        return Math.max(0L, selisih);
    }

    // Menghitung denda keterlambatan berdasarkan strategi aturan yang diberikan dari luar
    public long hitungDenda(AturanDendaCamping aturan) {
        if (aturan == null) {
            throw new IllegalArgumentException("Aturan denda tidak boleh kosong.");
        }
        return aturan.hitungDenda(hitungHariTerlambat());
    }

    // Menghitung akumulasi total biaya sewa seluruh item dikalikan durasi hari
    public double hitungTotalBiaya() {
        double totalPerHari = 0;
        for (DetailPersewaan detail : daftarDetail) {
            totalPerHari += detail.getSubTotal();
        }
        return totalPerHari * hitungDurasiHari();
    }

    public double hitungTotalDenganDenda(AturanDendaCamping aturan) {
        return hitungTotalBiaya() + hitungDenda(aturan);
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

    public String getNamaPenyewa() {
        return namaPenyewa;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public List<DetailPersewaan> getDaftarDetail() {
        return Collections.unmodifiableList(daftarDetail);
    }
}