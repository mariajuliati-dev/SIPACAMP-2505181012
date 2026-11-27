package gui;
import entity.AlatCamping;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
/**
 *Dibantu oleh AI untuk menyusun TableModel SIPACAMP yang menjembatani daftar AlatCamping dengan JTable.
 * @author Maria
 */
public class ModelTabelPerlengkapan extends AbstractTableModel {

    private static final String[] KOLOM = {
        "Kode", "Nama Perlengkapan", "Tipe", "Tarif / Hari", "Stok", "Status", "Siap Disewakan"
    };

    private transient List<AlatCamping> daftarData = new ArrayList<>();

    public void muatData(List<AlatCamping> dataBaru) {
        this.daftarData = (dataBaru != null) ? new ArrayList<>(dataBaru) : new ArrayList<>();
        fireTableDataChanged();
    }

    public AlatCamping ambilBaris(int indeksBaris) {
        if (indeksBaris < 0 || indeksBaris >= daftarData.size()) {
            return null;
        }
        return daftarData.get(indeksBaris);
    }

    @Override
    public int getRowCount() {
        return daftarData.size();
    }

    @Override
    public int getColumnCount() {
        return KOLOM.length;
    }

    @Override
    public String getColumnName(int kolom) {
        return KOLOM[kolom];
    }

    @Override
    public Object getValueAt(int baris, int kolom) {
        AlatCamping item = daftarData.get(baris);
        return switch (kolom) {
            case 0 -> item.getKodeAlat();
            case 1 -> item.getNamaAlat();
            case 2 -> item.getClass().getSimpleName();
            case 3 -> item.getHargaSewaPerHari();
            case 4 -> item.getStok();
            case 5 -> item.getStatus();
            case 6 -> item.siapDisewakan() ? "Ya" : "Tidak";
            default -> "";
        };
    }

    @Override
    public boolean isCellEditable(int baris, int kolom) {
        return false;
    }
}
