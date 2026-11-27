package eksepsi;
import java.time.LocalDate;
/**
 *
 * @author Maria
 */
public class PeriodeSewaTidakValidException extends SipacampException {

    public PeriodeSewaTidakValidException(LocalDate tanggalMulai, LocalDate tanggalSelesai) {
        super("Tanggal kembali (" + tanggalSelesai + ") tidak boleh lebih awal dari tanggal sewa ("
                + tanggalMulai + "). Atur tanggal kembali sama dengan atau setelah tanggal sewa.");
    }

    public PeriodeSewaTidakValidException(String pesan) {
        super(pesan);
    }
}
