package eksepsi;

/**
 *
 * @author Maria
 */
public class InputCampingTidakValidException extends SipacampException {
    private final String namaKolom;

    public InputCampingTidakValidException(String namaKolom, String pesan) {
        super(pesan);
        this.namaKolom = namaKolom;
    }

    public String getNamaKolom() {
        return namaKolom;
    }
}

