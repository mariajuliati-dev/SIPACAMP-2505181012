package eksepsi;
/**
 *
 * @author Maria
 */
public class SipacampException extends Exception {

    public SipacampException(String pesan) {
        super(pesan);
    }

    public SipacampException(String pesan, Throwable penyebab) {
        super(pesan, penyebab);
    }
}
