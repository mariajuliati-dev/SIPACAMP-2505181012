package berkas;
import eksepsi.*;
import entity.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author Maria
 */
public class LaporanCamping {

    public void tulisRingkasanTeks(String namaBerkas, List<AlatCamping> daftar)
            throws KesalahanPenyimpananException {
        try (PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(namaBerkas, StandardCharsets.UTF_8)))) {
            out.println("DAFTAR PERLENGKAPAN CAMPING SIPACAMP");
            out.println("Tanggal cetak: " + LocalDate.now());
            out.println();
            out.printf("%-10s %-26s %-18s %-12s %6s%n",
                    "KODE", "NAMA PERLENGKAPAN", "TIPE", "STATUS", "STOK");
            out.println("-".repeat(78));

            for (AlatCamping alat : daftar) {
                String tipe = (alat instanceof Tenda) ? "Tenda"
                        : (alat instanceof PeralatanMasak) ? "Peralatan Masak" : "Perlengkapan";
                out.printf("%-10s %-26s %-18s %-12s %6d%n",
                        alat.getKodeAlat(),
                        ringkas(alat.getNamaAlat(), 26),
                        tipe,
                        alat.getStatus(),
                        alat.getStok());
            }

            out.println("-".repeat(78));
            out.println("Total jenis perlengkapan: " + daftar.size());
        } catch (IOException ex) {
            throw new KesalahanPenyimpananException(
                    "Gagal menulis berkas laporan \"" + namaBerkas + "\". Periksa folder tujuan.", ex);
        }
    }

    public void tulisCsv(String namaBerkas, List<AlatCamping> daftar)
            throws KesalahanPenyimpananException {
        // CSV dipakai agar data mudah dibuka di spreadsheet dan tidak bergantung pada struktur kelas Java
        try (PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(namaBerkas, StandardCharsets.UTF_8)))) {
            out.println("kode_alat,nama_alat,tipe,harga_sewa_per_hari,stok,status");
            for (AlatCamping alat : daftar) {
                String tipe = (alat instanceof Tenda) ? "TENDA"
                        : (alat instanceof PeralatanMasak) ? "PERALATAN_MASAK" : "ALAT";
                out.printf("%s,%s,%s,%.0f,%d,%s%n",
                        kutipCsv(alat.getKodeAlat()),
                        kutipCsv(alat.getNamaAlat()),
                        kutipCsv(tipe),
                        alat.getHargaSewaPerHari(),
                        alat.getStok(),
                        kutipCsv(alat.getStatus()));
            }
        } catch (IOException ex) {
            throw new KesalahanPenyimpananException(
                    "Gagal mengekspor data CSV ke \"" + namaBerkas + "\".", ex);
        }
    }

    private String kutipCsv(String nilai) {
        String aman = (nilai == null) ? "" : nilai.replace("\"", "\"\"");
        return "\"" + aman + "\"";
    }

    private String ringkas(String teks, int maks) {
        if (teks == null) {
            return "";
        }
        return teks.length() <= maks ? teks : teks.substring(0, maks - 3) + "...";
    }
}
