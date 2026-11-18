package entity;

/**
 *
 * @author Maria
 */
public class DetailPersewaan {
    private int jumlah;
    private double subTotal;
    private AlatCamping alatCamping;

    public DetailPersewaan(int jumlah, AlatCamping alatCamping) {
        this.jumlah = jumlah;
        this.alatCamping = alatCamping;
        this.subTotal = getSubTotal();
    }

    //Menghitung total harga sewa per item alat berdasarkan jumlah sewa
    public double getSubTotal() {
        return this.jumlah * alatCamping.hargaSewaPerHari;
    }
}
