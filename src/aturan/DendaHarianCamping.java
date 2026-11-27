package aturan;
/**
 * Pattern Strategy digunakan agar perhitungan denda per hari dapat diganti dengan mudah tanpa
 * mengubah logika utama transaksi persewaan. Dua kemungkinan nyata yang membuatnya diperlukan adalah
 * (1) denda tetap harian standar untuk keterlambatan normal dan (2) denda bertingkat pada periode
 * ramai penyewaan perlengkapan camping.
 *
 * @author Maria
 */
public class DendaHarianCamping implements AturanDendaCamping {

    private static final long TARIF_PER_HARI = 15000L;

    @Override
    public long hitungDenda(long hariTerlambat) {
        if (hariTerlambat <= 0) {
            return 0L;
        }
        return hariTerlambat * TARIF_PER_HARI;
    }

    @Override
    public String namaAturan() {
        return "Denda harian tetap perlengkapan camping";
    }
}
