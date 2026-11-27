package basisdata;
import eksepsi.*;
import entity.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Dibantu oleh AI untuk mengimplementasikan operasi CRUD perlengkapan camping
 * menggunakan PreparedStatement guna mencegah SQL injection.
 *
 * @author Maria
 */
public class PerlengkapanDAOMySQL implements PerlengkapanDAO {

    @Override
    public void tambah(AlatCamping alat) throws KesalahanPenyimpananException {
        String sql = "INSERT INTO perlengkapan_camping (kode_alat, nama_alat, tipe_alat, "
                + "harga_sewa_per_hari, stok, status, kapasitas_orang, kondisi_pasak_lengkap, "
                + "bahan_material, kebersihan_terjaga) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = KoneksiSipacamp.getInstance().getSambungan().prepareStatement(sql)) {
            isiParameter(ps, alat);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new KesalahanPenyimpananException(
                    "Gagal menambahkan perlengkapan \"" + alat.getNamaAlat() + "\" ke basis data.", ex);
        }
    }

    @Override
    public void ubah(AlatCamping alat) throws KesalahanPenyimpananException {
        String sql = "UPDATE perlengkapan_camping SET nama_alat = ?, tipe_alat = ?, "
                + "harga_sewa_per_hari = ?, stok = ?, status = ?, kapasitas_orang = ?, "
                + "kondisi_pasak_lengkap = ?, bahan_material = ?, kebersihan_terjaga = ? "
                + "WHERE kode_alat = ?";

        try (PreparedStatement ps = KoneksiSipacamp.getInstance().getSambungan().prepareStatement(sql)) {
            ps.setString(1, alat.getNamaAlat());
            if (alat instanceof Tenda tenda) {
                ps.setString(2, "TENDA");
                ps.setDouble(3, tenda.getHargaSewaPerHari());
                ps.setInt(4, tenda.getStok());
                ps.setString(5, tenda.getStatus());
                ps.setInt(6, tenda.getKapasitasOrang());
                ps.setBoolean(7, tenda.isKondisiPasakLengkap());
                ps.setNull(8, Types.VARCHAR);
                ps.setNull(9, Types.BOOLEAN);
            } else if (alat instanceof PeralatanMasak masak) {
                ps.setString(2, "PERALATAN_MASAK");
                ps.setDouble(3, masak.getHargaSewaPerHari());
                ps.setInt(4, masak.getStok());
                ps.setString(5, masak.getStatus());
                ps.setNull(6, Types.INTEGER);
                ps.setNull(7, Types.BOOLEAN);
                ps.setString(8, masak.getBahanMaterial());
                ps.setBoolean(9, masak.isKebersihanTerjaga());
            } else {
                throw new KesalahanPenyimpananException(
                        "Tipe perlengkapan \"" + alat.getNamaAlat() + "\" belum didukung.");
            }
            ps.setString(10, alat.getKodeAlat());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new KesalahanPenyimpananException(
                    "Gagal memperbarui perlengkapan dengan kode \"" + alat.getKodeAlat() + "\".", ex);
        }
    }

    @Override
    public void hapus(String kodeAlat) throws KesalahanPenyimpananException {
        String sql = "DELETE FROM perlengkapan_camping WHERE kode_alat = ?";
        try (PreparedStatement ps = KoneksiSipacamp.getInstance().getSambungan().prepareStatement(sql)) {
            ps.setString(1, kodeAlat);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new KesalahanPenyimpananException(
                    "Gagal menghapus perlengkapan dengan kode \"" + kodeAlat + "\".", ex);
        }
    }

    @Override
    public AlatCamping cariKode(String kodeAlat) throws KesalahanPenyimpananException {
        String sql = "SELECT * FROM perlengkapan_camping WHERE kode_alat = ?";
        try (PreparedStatement ps = KoneksiSipacamp.getInstance().getSambungan().prepareStatement(sql)) {
            ps.setString(1, kodeAlat);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return bentukAlat(rs);
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new KesalahanPenyimpananException(
                    "Gagal mencari perlengkapan berkode \"" + kodeAlat + "\".", ex);
        }
    }

    @Override
    public List<AlatCamping> cariNama(String kataKunci) throws KesalahanPenyimpananException {
        String sql = "SELECT * FROM perlengkapan_camping WHERE nama_alat LIKE ? ORDER BY kode_alat ASC";
        List<AlatCamping> daftar = new ArrayList<>();
        try (PreparedStatement ps = KoneksiSipacamp.getInstance().getSambungan().prepareStatement(sql)) {
            ps.setString(1, "%" + kataKunci + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    daftar.add(bentukAlat(rs));
                }
            }
            return daftar;
        } catch (SQLException ex) {
            throw new KesalahanPenyimpananException(
                    "Gagal mencari perlengkapan dengan kata kunci \"" + kataKunci + "\".", ex);
        }
    }

    @Override
    public List<AlatCamping> semua() throws KesalahanPenyimpananException {
        String sql = "SELECT * FROM perlengkapan_camping ORDER BY kode_alat ASC";
        List<AlatCamping> daftar = new ArrayList<>();
        try (PreparedStatement ps = KoneksiSipacamp.getInstance().getSambungan().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                daftar.add(bentukAlat(rs));
            }
            return daftar;
        } catch (SQLException ex) {
            throw new KesalahanPenyimpananException(
                    "Gagal mengambil daftar perlengkapan dari basis data.", ex);
        }
    }

    @Override
    public String sumberData() {
        return "MySQL (db_sipacamp)";
    }

    private void isiParameter(PreparedStatement ps, AlatCamping alat)
            throws SQLException, KesalahanPenyimpananException {
        ps.setString(1, alat.getKodeAlat());
        ps.setString(2, alat.getNamaAlat());
        if (alat instanceof Tenda tenda) {
            ps.setString(3, "TENDA");
            ps.setDouble(4, tenda.getHargaSewaPerHari());
            ps.setInt(5, tenda.getStok());
            ps.setString(6, tenda.getStatus());
            ps.setInt(7, tenda.getKapasitasOrang());
            ps.setBoolean(8, tenda.isKondisiPasakLengkap());
            ps.setNull(9, Types.VARCHAR);
            ps.setNull(10, Types.BOOLEAN);
        } else if (alat instanceof PeralatanMasak masak) {
            ps.setString(3, "PERALATAN_MASAK");
            ps.setDouble(4, masak.getHargaSewaPerHari());
            ps.setInt(5, masak.getStok());
            ps.setString(6, masak.getStatus());
            ps.setNull(7, Types.INTEGER);
            ps.setNull(8, Types.BOOLEAN);
            ps.setString(9, masak.getBahanMaterial());
            ps.setBoolean(10, masak.isKebersihanTerjaga());
        } else {
            throw new KesalahanPenyimpananException(
                    "Tipe perlengkapan \"" + alat.getNamaAlat() + "\" belum didukung.");
        }
    }

    private AlatCamping bentukAlat(ResultSet rs) throws SQLException {
        String kode = rs.getString("kode_alat");
        String nama = rs.getString("nama_alat");
        String tipe = rs.getString("tipe_alat");
        double harga = rs.getDouble("harga_sewa_per_hari");
        int stok = rs.getInt("stok");
        String status = rs.getString("status");

        if ("TENDA".equalsIgnoreCase(tipe)) {
            int kapasitas = rs.getInt("kapasitas_orang");
            boolean pasak = rs.getBoolean("kondisi_pasak_lengkap");
            return new Tenda(kode, nama, harga, stok, status, kapasitas, pasak);
        }
        String bahan = rs.getString("bahan_material");
        boolean bersih = rs.getBoolean("kebersihan_terjaga");
        return new PeralatanMasak(kode, nama, harga, stok, status, bahan, bersih);
    }
}
