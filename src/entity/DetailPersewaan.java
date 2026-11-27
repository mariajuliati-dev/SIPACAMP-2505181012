package entity;

import eksepsi.*;

/**
 * Merepresentasikan satu baris rincian perlengkapan pada transaksi persewaan SIPACAMP.
 * @author Maria
 */
public class DetailPersewaan {

    private int jumlah;
    private double subTotal;

    // ASOSIASI: objek AlatCamping dibuat di luar dan dirujuk pada baris detail ini
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

    public String baris() {
        return jumlah + "x " + alatCamping.getNamaAlat() + " (Rp" + subTotal + ")";
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