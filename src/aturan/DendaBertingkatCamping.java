package aturan;
/**
 * Dibantu oleh AI untuk menyusun implementasi DendaBertingkatCamping pada proyek mandiri SIPACAMP.
 * Pattern Strategy dipakai untuk memisahkan variasi rumus denda keterlambatan sehingga aturan baru
 * dapat ditambahkan secara independen. Dua kemungkinan nyata yang membuatnya diperlukan adalah
 * (1) aturan denda harian datar pada hari biasa dan (2) aturan denda bertingkat dengan kenaikan
 * tarif setelah melewati batas hari tertentu pada masa liburan/peak season.
 *
 * @author Maria
 */
public class DendaBertingkatCamping implements AturanDendaCamping {

    @Override
    public long hitungDenda(long hariTerlambat) {
        if (hariTerlambat <= 0) {
            return 0L;
        }
        if (hariTerlambat <= 3) {
            return hariTerlambat * 10000L;
        }
        return 30000L + ((hariTerlambat - 3) * 20000L);
    }

    @Override
    public String namaAturan() {
        return "Denda bertingkat keterlambatan camping";
    }
}