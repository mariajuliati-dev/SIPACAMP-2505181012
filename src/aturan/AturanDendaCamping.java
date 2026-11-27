package aturan;
/**
 * Pattern Strategy dipilih karena kebijakan perhitungan denda keterlambatan perlengkapan camping
 * merupakan aturan bisnis yang dapat berubah sesuai kebijakan operasional tanpa mengubah kelas Persewaan.
 * Dua kemungkinan nyata yang membuat pattern ini diperlukan adalah: (1) aturan denda tarif tetap harian
 * untuk operasional reguler dan (2) aturan denda bertingkat saat permintaan tinggi/peak season agar
 * perlengkapan camping kembali tepat waktu.
 *
 * @author Maria
 */
public interface AturanDendaCamping {

    long hitungDenda(long hariTerlambat);

    String namaAturan();
}
