package eksepsi;
/**
 *
 * @author Maria
 */
public class PersewaanSudahDitutupException extends SipacampException {

    private static final long serialVersionUID = 1L;

    public PersewaanSudahDitutupException(String idPersewaan, String tanggalKembali) {
        super(
            "Persewaan " + idPersewaan + " sudah ditutup sejak tanggal " + tanggalKembali + "."
        );
    }
}
