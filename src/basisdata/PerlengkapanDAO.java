package basisdata;

import eksepsi.KesalahanPenyimpananException;
import entity.AlatCamping;
import java.util.List;

/**
 * Dibantu oleh AI untuk mendefinisikan kontrak akses data perlengkapan camping
 * tanpa ketergantungan langsung pada API JDBC.
 *
 * @author Maria
 */
public interface PerlengkapanDAO {

    void tambah(AlatCamping alat) throws KesalahanPenyimpananException;
    void ubah(AlatCamping alat) throws KesalahanPenyimpananException;
    void hapus(String kodeAlat) throws KesalahanPenyimpananException;
    AlatCamping cariKode(String kodeAlat) throws KesalahanPenyimpananException;
    List<AlatCamping> cariNama(String kataKunci) throws KesalahanPenyimpananException;
    List<AlatCamping> semua() throws KesalahanPenyimpananException;
    String sumberData();
}
