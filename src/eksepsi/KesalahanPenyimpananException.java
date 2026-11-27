package eksepsi;
/**
 * Dibantu oleh AI untuk menyusun eksepsi turunan SipacampException khusus
 * kegagalan berkas dan basis data dengan menyimpan penyebab aslinya.
 *
 * @author Maria
 */
public class KesalahanPenyimpananException extends SipacampException {

    public KesalahanPenyimpananException(String pesan, Throwable penyebab) {
        super(pesan, penyebab);
    }

    public KesalahanPenyimpananException(String pesan) {
        super(pesan);
    }
}
