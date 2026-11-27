package eksepsi;
/**
 * Dibantu oleh AI untuk menyusun pesan eksepsi saat jumlah sewa melebihi stok.
 *
 * @author Maria
 */
public class StokSewaKurangException extends SipacampException {

    public StokSewaKurangException(String namaAlat, int jumlahDiminta, int stokTersedia) {
        super("Permintaan " + jumlahDiminta + " unit untuk \"" + namaAlat
                + "\" melebihi stok tersedia (" + stokTersedia
                + " unit). Masukkan jumlah antara 1 sampai " + stokTersedia + ".");
    }
}
