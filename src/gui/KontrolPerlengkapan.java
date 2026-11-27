package gui;
import basisdata.*;
import eksepsi.*;
import entity.AlatCamping;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
/**
 *
 * @author Maria
 */
public class KontrolPerlengkapan {

    private final PerlengkapanDAO daoPerlengkapan;
    private final List<AlatCamping> cachePerlengkapan = new ArrayList<>();

    public KontrolPerlengkapan(PerlengkapanDAO daoPerlengkapan) {
        this.daoPerlengkapan = daoPerlengkapan;
    }

    public void muatDariBasisData() throws KesalahanPenyimpananException {
        List<AlatCamping> hasilDB = daoPerlengkapan.semua();
        cachePerlengkapan.clear();
        if (hasilDB != null) {
            cachePerlengkapan.addAll(hasilDB);
        }
    }

    public List<AlatCamping> daftarTerurut() {
        List<AlatCamping> salinan = new ArrayList<>(cachePerlengkapan);
        salinan.sort(
            Comparator.comparingDouble(AlatCamping::getHargaSewaPerHari)
                .thenComparingInt(AlatCamping::getStok)
        );
        return salinan;
    }

    public List<AlatCamping> cariPerlengkapan(String kataKunci) {
        if (kataKunci == null || kataKunci.isBlank()) {
            return daftarTerurut();
        }
        String kunci = kataKunci.trim().toLowerCase(Locale.ROOT);
        List<AlatCamping> cocok = new ArrayList<>();
        for (AlatCamping alat : cachePerlengkapan) {
            boolean samaKode = alat.getKodeAlat() != null
                && alat.getKodeAlat().toLowerCase(Locale.ROOT).contains(kunci);
            boolean samaNama = alat.getNamaAlat() != null
                && alat.getNamaAlat().toLowerCase(Locale.ROOT).contains(kunci);
            if (samaKode || samaNama) {
                cocok.add(alat);
            }
        }
        return cocok;
    }

    public void tambahPerlengkapan(AlatCamping alat) throws KesalahanPenyimpananException {
        validasiAlat(alat);
        daoPerlengkapan.tambah(alat);
        cachePerlengkapan.removeIf(item -> item.getKodeAlat().equalsIgnoreCase(alat.getKodeAlat()));
        cachePerlengkapan.add(alat);
    }

    public void perbaruiPerlengkapan(AlatCamping alat) throws KesalahanPenyimpananException {
        validasiAlat(alat);
        daoPerlengkapan.ubah(alat);
        for (int i = 0; i < cachePerlengkapan.size(); i++) {
            if (cachePerlengkapan.get(i).getKodeAlat().equalsIgnoreCase(alat.getKodeAlat())) {
                cachePerlengkapan.set(i, alat);
                return;
            }
        }
        cachePerlengkapan.add(alat);
    }

    public void hapusPerlengkapan(AlatCamping alat) throws KesalahanPenyimpananException {
        if (alat == null || alat.getKodeAlat() == null || alat.getKodeAlat().isBlank()) {
            throw new KesalahanPenyimpananException("Data perlengkapan yang akan dihapus tidak valid.");
        }
        daoPerlengkapan.hapus(alat.getKodeAlat());
        cachePerlengkapan.removeIf(item -> item.getKodeAlat().equalsIgnoreCase(alat.getKodeAlat()));
    }

    public int totalItem() {
        return cachePerlengkapan.size();
    }

    public int totalSiapDisewakan() {
        int hitung = 0;
        for (AlatCamping alat : cachePerlengkapan) {
            if (alat.siapDisewakan()) {
                hitung++;
            }
        }
        return hitung;
    }

    private void validasiAlat(AlatCamping alat) throws KesalahanPenyimpananException {
        if (alat == null) {
            throw new KesalahanPenyimpananException("Objek perlengkapan tidak boleh kosong.");
        }
        if (alat.getKodeAlat() == null || alat.getKodeAlat().isBlank()) {
            throw new KesalahanPenyimpananException("Kode alat wajib diisi.");
        }
        if (alat.getNamaAlat() == null || alat.getNamaAlat().isBlank()) {
            throw new KesalahanPenyimpananException("Nama perlengkapan wajib diisi.");
        }
        if (alat.getHargaSewaPerHari() <= 0) {
            throw new KesalahanPenyimpananException("Harga sewa per hari harus lebih dari 0.");
        }
        if (alat.getStok() < 0) {
            throw new KesalahanPenyimpananException("Stok tidak boleh bernilai negatif.");
        }
    }
}