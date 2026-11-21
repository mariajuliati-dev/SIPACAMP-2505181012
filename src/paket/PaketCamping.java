package paket;
import entity.AlatCamping;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author Maria
 */
/**
 * Dibantu oleh AI untuk menyusun kelas generic PaketCamping dengan batas tipe
 * extends AlatCamping dan implementasi Iterable untuk manajemen paket sewa.
 */

public class PaketCamping<T extends AlatCamping> implements Iterable<T> {
    private final String namaPaket;
    private final int maksJenisAlat;
    private final List<T> daftarIsi = new ArrayList<>();

    public PaketCamping(String namaPaket, int maksJenisAlat) {
        this.namaPaket = namaPaket;
        this.maksJenisAlat = maksJenisAlat;
    }

    public boolean masukkanAlat(T alat) {
        if (daftarIsi.size() >= maksJenisAlat || !alat.siapDisewakan()) {
            return false;
        }
        return daftarIsi.add(alat);
    }

    public double hitungTotalTarifPaket() {
        double total = 0;
        for (T item : daftarIsi) {
            total += item.getHargaSewaPerHari();
        }
        return total;
    }

    public String getNamaPaket() {
        return namaPaket;
    }

    public List<T> getDaftarIsi() {
        return Collections.unmodifiableList(daftarIsi);
    }

    @Override
    public Iterator<T> iterator() {
        return daftarIsi.iterator();
    }

}
