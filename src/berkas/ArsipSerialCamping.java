package berkas;
import eksepsi.KesalahanPenyimpananException;
import entity.AlatCamping;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maria
 */
public class ArsipSerialCamping {

    private final String lokasiBerkas;

    public ArsipSerialCamping(String lokasiBerkas) {
        this.lokasiBerkas = lokasiBerkas;
    }

    public void simpanDaftar(List<AlatCamping> daftar) throws KesalahanPenyimpananException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(lokasiBerkas))) {
            oos.writeObject(new ArrayList<>(daftar));
        } catch (IOException ex) {
            throw new KesalahanPenyimpananException(
                    "Gagal menyimpan arsip objek ke \"" + lokasiBerkas + "\".", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<AlatCamping> muatDaftar() throws KesalahanPenyimpananException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(lokasiBerkas))) {
            return (List<AlatCamping>) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new KesalahanPenyimpananException(
                    "Gagal memuat arsip objek dari \"" + lokasiBerkas
                    + "\". Berkas mungkin belum ada atau struktur kelas sudah berubah.", ex);
        }
    }
}
