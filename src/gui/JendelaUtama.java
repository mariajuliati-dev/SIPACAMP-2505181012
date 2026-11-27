package gui;
import entity.AlatCamping;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import layanan.PemantauPersewaan;
/**
 * Jendela utama View pada arsitektur MVC SIPACAMP yang menampilkan katalog
 * perlengkapan camping dan menghubungkan aksi pengguna ke KontrolPerlengkapan.
 *
 * @author Maria
 */
public class JendelaUtama extends JFrame {

    private final transient KontrolPerlengkapan kontrol;
    private final ModelTabelPerlengkapan modelTabel = new ModelTabelPerlengkapan();
    private final JTable tabelPerlengkapan = new JTable(modelTabel);
    private final JTextField fieldCari = new JTextField(18);
    private final JLabel labelStatus = new JLabel("Siap.");
    private final transient PemantauPersewaan pemantau;

    public JendelaUtama(KontrolPerlengkapan kontrol) {
        super("SIPACAMP - Sistem Persewaan Perlengkapan Camping");
        this.kontrol = kontrol;
        this.pemantau = new PemantauPersewaan(
            15000L,
            pesan -> SwingUtilities.invokeLater(() -> labelStatus.setText(pesan))
        );

        susunTampilan();
        pemantau.mulai();
        muatDataAsync();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(920, 540);
        setLocationRelativeTo(null);
    }

    private void susunTampilan() {
        JButton tombolCari = new JButton("Cari");
        JButton tombolTambah = new JButton("Tambah");
        JButton tombolUbah = new JButton("Ubah");
        JButton tombolHapus = new JButton("Hapus");
        JButton tombolMuatUlang = new JButton("Muat Ulang");

        tombolCari.addActionListener(e -> aksiCari());
        fieldCari.addActionListener(e -> aksiCari());
        tombolTambah.addActionListener(e -> aksiTambah());
        tombolUbah.addActionListener(e -> aksiUbah());
        tombolHapus.addActionListener(e -> aksiHapus());
        tombolMuatUlang.addActionListener(e -> muatDataAsync());

        JPanel panelAtas = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        panelAtas.add(new JLabel("Cari:"));
        panelAtas.add(fieldCari);
        panelAtas.add(tombolCari);
        panelAtas.add(tombolTambah);
        panelAtas.add(tombolUbah);
        panelAtas.add(tombolHapus);
        panelAtas.add(tombolMuatUlang);

        tabelPerlengkapan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelPerlengkapan.setRowHeight(24);

        setLayout(new BorderLayout());
        add(panelAtas, BorderLayout.NORTH);
        add(new JScrollPane(tabelPerlengkapan), BorderLayout.CENTER);
        add(labelStatus, BorderLayout.SOUTH);
    }

    private void muatDataAsync() {
        labelStatus.setText("Memuat data perlengkapan...");
        new SwingWorker<List<AlatCamping>, Void>() {
            @Override
            protected List<AlatCamping> doInBackground() throws Exception {
                kontrol.muatDariBasisData();
                return kontrol.daftarTerurut();
            }

            @Override
            protected void done() {
                try {
                    modelTabel.muatData(get());
                    perbaruiRingkasanStatus();
                } catch (Exception ex) {
                    tampilkanPesanGalat("Gagal memuat data: " + ambilPesanAsli(ex));
                }
            }
        }.execute();
    }

    private void aksiCari() {
        modelTabel.muatData(kontrol.cariPerlengkapan(fieldCari.getText()));
    }

    private void aksiTambah() {
        DialogPerlengkapan dialog = new DialogPerlengkapan(this, null);
        dialog.setVisible(true);
        AlatCamping alatBaru = dialog.getHasilAlat();
        if (alatBaru == null) {
            return;
        }
        jalankanSimpanAsync(alatBaru, true);
    }

    private void aksiUbah() {
        AlatCamping terpilih = modelTabel.ambilBaris(tabelPerlengkapan.getSelectedRow());
        if (terpilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih baris perlengkapan terlebih dahulu.");
            return;
        }
        DialogPerlengkapan dialog = new DialogPerlengkapan(this, terpilih);
        dialog.setVisible(true);
        AlatCamping alatDiubah = dialog.getHasilAlat();
        if (alatDiubah == null) {
            return;
        }
        jalankanSimpanAsync(alatDiubah, false);
    }

    private void aksiHapus() {
        AlatCamping terpilih = modelTabel.ambilBaris(tabelPerlengkapan.getSelectedRow());
        if (terpilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih baris perlengkapan terlebih dahulu.");
            return;
        }
        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Hapus perlengkapan dengan kode " + terpilih.getKodeAlat() + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );
        if (konfirmasi != JOptionPane.YES_OPTION) {
            return;
        }
        jalankanHapusAsync(terpilih);
    }

    private void jalankanSimpanAsync(AlatCamping alat, boolean tambahBaru) {
        labelStatus.setText("Menyimpan data ke basis data...");
        new SwingWorker<List<AlatCamping>, Void>() {
            @Override
            protected List<AlatCamping> doInBackground() throws Exception {
                if (tambahBaru) {
                    kontrol.tambahPerlengkapan(alat);
                } else {
                    kontrol.perbaruiPerlengkapan(alat);
                }
                return kontrol.daftarTerurut();
            }

            @Override
            protected void done() {
                try {
                    modelTabel.muatData(get());
                    perbaruiRingkasanStatus();
                } catch (Exception ex) {
                    tampilkanPesanGalat("Gagal menyimpan data: " + ambilPesanAsli(ex));
                }
            }
        }.execute();
    }

    private void jalankanHapusAsync(AlatCamping alat) {
        labelStatus.setText("Menghapus data dari basis data...");
        new SwingWorker<List<AlatCamping>, Void>() {
            @Override
            protected List<AlatCamping> doInBackground() throws Exception {
                kontrol.hapusPerlengkapan(alat);
                return kontrol.daftarTerurut();
            }

            @Override
            protected void done() {
                try {
                    modelTabel.muatData(get());
                    perbaruiRingkasanStatus();
                } catch (Exception ex) {
                    tampilkanPesanGalat("Gagal menghapus data: " + ambilPesanAsli(ex));
                }
            }
        }.execute();
    }

    private void perbaruiRingkasanStatus() {
        labelStatus.setText(
            "Total item: " + kontrol.totalItem() + " | Siap disewakan: " + kontrol.totalSiapDisewakan()
        );
    }

    private String ambilPesanAsli(Exception ex) {
        if (ex instanceof ExecutionException && ex.getCause() != null) {
            return ex.getCause().getMessage();
        }
        return ex.getMessage();
    }

    private void tampilkanPesanGalat(String pesan) {
        labelStatus.setText(pesan);
        JOptionPane.showMessageDialog(this, pesan, "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void dispose() {
        pemantau.hentikan();
        super.dispose();
    }
}