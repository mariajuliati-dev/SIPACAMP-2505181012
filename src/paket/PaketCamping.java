package paket;
import eksepsi.*;
import entity.AlatCamping;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Maria
 */
public class PaketCamping<T extends AlatCamping> implements Iterable<T> {
    private final String namaPaket;
    private final int maksJenisAlat;
    private final List<T> daftarIsi = new ArrayList<>();

    public PaketCamping(String namaPaket, int maksJenisAlat) {
        this.namaPaket = namaPaket;
        this.maksJenisAlat = maksJenisAlat;
    }

    public void masukkanAlat(T alat) throws AlatTidakSiapSewaException, StokSewaKurangException {
        if (alat == null) {
            throw new AlatTidakSiapSewaException("Tidak diketahui",
                    "data perlengkapan camping belum dipilih");
        }
        if (!alat.siapDisewakan()) {
            throw new AlatTidakSiapSewaException(alat.getNamaAlat(),
                    "kondisi atau stok belum siap disewakan");
        }
        if (daftarIsi.size() >= maksJenisAlat) {
            throw new StokSewaKurangException("Paket \"" + namaPaket + "\"",
                    daftarIsi.size() + 1, maksJenisAlat);
        }

        daftarIsi.add(alat);
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