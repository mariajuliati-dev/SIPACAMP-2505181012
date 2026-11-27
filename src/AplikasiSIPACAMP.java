import basisdata.PerlengkapanDAO;
import basisdata.PerlengkapanDAOMySQL;
import gui.JendelaUtama;
import gui.KontrolPerlengkapan;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/**
 *Dibantu oleh AI untuk menyusun kelas peluncur utama aplikasi GUI SIPACAMP.
 * @author Maria
 */
public class AplikasiSIPACAMP {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Tema bawaan sistem tidak dapat diterapkan.");
        }

        SwingUtilities.invokeLater(() -> {
            PerlengkapanDAO dao = new PerlengkapanDAOMySQL();
            KontrolPerlengkapan kontrol = new KontrolPerlengkapan(dao);
            new JendelaUtama(kontrol).setVisible(true);
        });
    }
}