package entity;
import eksepsi.*;
/**
 *
 * @author Maria
 */
public class DetailPersewaan {
    private int jumlah;
    private double subTotal;
    private AlatCamping alatCamping;

    public DetailPersewaan(int jumlah, AlatCamping alatCamping)
            throws AlatTidakSiapSewaException, StokSewaKurangException {
        if (alatCamping == null) {
            throw new AlatTidakSiapSewaException("Tidak diketahui",
                    "data perlengkapan belum dipilih");
        }
        if (!alatCamping.siapDisewakan()) {
            throw new AlatTidakSiapSewaException(alatCamping.getNamaAlat(),
                    "kondisi atau stok belum siap disewakan");
        }
        if (jumlah <= 0) {
            throw new StokSewaKurangException(alatCamping.getNamaAlat(),
                    jumlah, alatCamping.getStok());
        }
        if (jumlah > alatCamping.getStok()) {
            throw new StokSewaKurangException(alatCamping.getNamaAlat(),
                    jumlah, alatCamping.getStok());
        }

        this.jumlah = jumlah;
        this.alatCamping = alatCamping;
        this.subTotal = hitungSubTotal();
    }

    // Menghitung total harga sewa per item alat berdasarkan jumlah sewa
    public double hitungSubTotal() {
        return this.jumlah * alatCamping.getHargaSewaPerHari();
    }

    public double getSubTotal() {
        return subTotal;
    }

    public int getJumlah() {
        return jumlah;
    }

    public AlatCamping getAlatCamping() {
        return alatCamping;
    }
}
