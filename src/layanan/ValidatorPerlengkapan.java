package layanan;
import eksepsi.*;
import entity.*;
import java.time.Year;
/**
 * Dibantu oleh AI untuk menyusun aturan validasi data perlengkapan camping.
 *
 * @author Maria
 */
public class ValidatorPerlengkapan {

    public void validasi(AlatCamping alat, int tahunPengadaan) throws InputCampingTidakValidException {
        if (alat == null) {
            throw new InputCampingTidakValidException("Perlengkapan",
                    "Data perlengkapan camping belum dipilih.");
        }
        if (alat.getNamaAlat() == null || alat.getNamaAlat().isBlank()) {
            throw new InputCampingTidakValidException("Nama Alat",
                    "Nama perlengkapan wajib diisi, misalnya Tenda Dome 4P.");
        }
        if (alat.getHargaSewaPerHari() <= 0) {
            throw new InputCampingTidakValidException("Tarif Sewa",
                    "Tarif sewa per hari harus lebih dari Rp 0.");
        }
        int tahunSekarang = Year.now().getValue();
        if (tahunPengadaan < 2010 || tahunPengadaan > tahunSekarang) {
            throw new InputCampingTidakValidException("Tahun Pengadaan",
                    "Tahun pengadaan harus berada di antara 2010 dan " + tahunSekarang + ".");
        }
    }
}
