package entity;

/**
 *
 * @author Maria
 */
public class DetailPenyewaan {
    private int jumlah;
    private double subTotal;
    private AlatCamping alatCamping;

    public DetailPenyewaan(int jumlah, AlatCamping alatCamping) {
        this.jumlah = jumlah;
        this.alatCamping = alatCamping;
        this.subTotal = getSubTotal();
    }

    //Menghitung total harga sewa per item alat berdasarkan jumlah sewa
    public double getSubTotal() {
        return this.jumlah * alatCamping.hargaSewaPerHari;
    }
}
