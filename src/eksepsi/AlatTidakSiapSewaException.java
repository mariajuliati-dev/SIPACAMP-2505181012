package eksepsi;

/**
 *
 * @author Maria
 */
public class AlatTidakSiapSewaException extends SipacampException {

    public AlatTidakSiapSewaException(String namaAlat, String keteranganKondisi) {
        super("Alat camping \"" + namaAlat + "\" belum bisa disewakan ("
                + keteranganKondisi + "). Silakan pilih unit lain yang siap pakai.");
    }
}
