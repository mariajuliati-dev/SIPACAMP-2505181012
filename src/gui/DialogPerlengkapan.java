package gui;
import entity.AlatCamping;
import entity.PeralatanMasak;
import entity.Tenda;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 *
 * @author Maria
 */ 
public class DialogPerlengkapan extends JDialog {

    private final JTextField fieldKode = new JTextField(14);
    private final JTextField fieldNama = new JTextField(22);
    private final JComboBox<String> comboTipe = new JComboBox<>(new String[]{"Tenda", "PeralatanMasak"});
    private final JTextField fieldHarga = new JTextField(12);
    private final JTextField fieldStok = new JTextField(8);
    private final JComboBox<String> comboStatus = new JComboBox<>(new String[]{"Tersedia", "Disewa", "Perawatan"});

    private final JLabel labelAtributSatu = new JLabel("Kapasitas (orang)");
    private final JTextField fieldAtributSatu = new JTextField(10);

    private final JLabel labelAtributDua = new JLabel("Pasak lengkap (ya/tidak)");
    private final JTextField fieldAtributDua = new JTextField(10);

    private transient AlatCamping hasilAlat;

    public DialogPerlengkapan(JFrame induk, AlatCamping dataAwal) {
        super(induk, dataAwal == null ? "Tambah Perlengkapan Camping" : "Ubah Perlengkapan Camping", true);
        susunKomponen();
        pasangListener();
        if (dataAwal != null) {
            isiDataAwal(dataAwal);
        }
        sesuaikanLabelTipe();
        pack();
        setLocationRelativeTo(induk);
    }

    private void susunKomponen() {
        JPanel panelForm = new JPanel(new GridLayout(0, 2, 8, 8));
        panelForm.setBorder(BorderFactory.createEmptyBorder(14, 14, 8, 14));

        panelForm.add(new JLabel("Kode Alat"));
        panelForm.add(fieldKode);

        panelForm.add(new JLabel("Nama Perlengkapan"));
        panelForm.add(fieldNama);

        panelForm.add(new JLabel("Tipe Perlengkapan"));
        panelForm.add(comboTipe);

        panelForm.add(new JLabel("Harga Sewa / Hari (Rp)"));
        panelForm.add(fieldHarga);

        panelForm.add(new JLabel("Stok"));
        panelForm.add(fieldStok);

        panelForm.add(new JLabel("Status"));
        panelForm.add(comboStatus);

        panelForm.add(labelAtributSatu);
        panelForm.add(fieldAtributSatu);

        panelForm.add(labelAtributDua);
        panelForm.add(fieldAtributDua);

        JButton tombolSimpan = new JButton("Simpan");
        JButton tombolBatal = new JButton("Batal");
        tombolSimpan.addActionListener(e -> prosesSimpan());
        tombolBatal.addActionListener(e -> {
            hasilAlat = null;
            dispose();
        });

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        panelTombol.add(tombolBatal);
        panelTombol.add(tombolSimpan);

        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.CENTER);
        add(panelTombol, BorderLayout.SOUTH);
    }

    private void pasangListener() {
        comboTipe.addActionListener(e -> sesuaikanLabelTipe());
    }

    private void sesuaikanLabelTipe() {
        String tipe = (String) comboTipe.getSelectedItem();
        if ("PeralatanMasak".equals(tipe)) {
            labelAtributSatu.setText("Bahan Material");
            labelAtributDua.setText("Kebersihan terjaga (ya/tidak)");
        } else {
            labelAtributSatu.setText("Kapasitas (orang)");
            labelAtributDua.setText("Pasak lengkap (ya/tidak)");
        }
    }

    private void isiDataAwal(AlatCamping alat) {
        fieldKode.setText(alat.getKodeAlat());
        fieldKode.setEditable(false);
        fieldNama.setText(alat.getNamaAlat());
        fieldHarga.setText(String.valueOf(alat.getHargaSewaPerHari()));
        fieldStok.setText(String.valueOf(alat.getStok()));
        comboStatus.setSelectedItem(alat.getStatus());

        if (alat instanceof Tenda tenda) {
            comboTipe.setSelectedItem("Tenda");
            sesuaikanLabelTipe();
            fieldAtributSatu.setText(String.valueOf(tenda.getKapasitasOrang()));
            fieldAtributDua.setText(tenda.isKondisiPasakLengkap() ? "ya" : "tidak");
        } else if (alat instanceof PeralatanMasak masak) {
            comboTipe.setSelectedItem("PeralatanMasak");
            sesuaikanLabelTipe();
            fieldAtributSatu.setText(masak.getBahanMaterial());
            fieldAtributDua.setText(masak.isKebersihanTerjaga() ? "ya" : "tidak");
        }
    }

    private void prosesSimpan() {
        try {
            String kode = fieldKode.getText().trim();
            String nama = fieldNama.getText().trim();
            double harga = Double.parseDouble(fieldHarga.getText().trim());
            int stok = Integer.parseInt(fieldStok.getText().trim());
            String status = (String) comboStatus.getSelectedItem();
            String tipe = (String) comboTipe.getSelectedItem();

            if ("PeralatanMasak".equals(tipe)) {
                String bahan = fieldAtributSatu.getText().trim();
                boolean bersih = parseYaTidak(fieldAtributDua.getText().trim());
                hasilAlat = new PeralatanMasak(kode, nama, harga, stok, status, bahan, bersih);
            } else {
                int kapasitas = Integer.parseInt(fieldAtributSatu.getText().trim());
                boolean pasakLengkap = parseYaTidak(fieldAtributDua.getText().trim());
                hasilAlat = new Tenda(kode, nama, harga, stok, status, kapasitas, pasakLengkap);
            }
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Pastikan harga, stok, dan kapasitas diisi dengan angka yang valid.",
                "Format Angka Tidak Valid",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private boolean parseYaTidak(String nilai) {
        return "ya".equalsIgnoreCase(nilai) || "y".equalsIgnoreCase(nilai) || "true".equalsIgnoreCase(nilai);
    }

    public AlatCamping getHasilAlat() {
        return hasilAlat;
    }
}
