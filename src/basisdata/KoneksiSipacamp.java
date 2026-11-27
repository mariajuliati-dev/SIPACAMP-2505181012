package basisdata;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Maria
 */
public class KoneksiSipacamp {

    private static KoneksiSipacamp instance;
    private String url = "jdbc:mysql://localhost:3306/db_sipacamp?serverTimezone=Asia/Jakarta";
    private String pengguna = "root";
    private String sandi = "";
    private Connection sambungan;

    private KoneksiSipacamp() {
    }

    public static synchronized KoneksiSipacamp getInstance() {
        if (instance == null) {
            instance = new KoneksiSipacamp();
        }
        return instance;
    }

    public void konfigurasi(String url, String pengguna, String sandi) {
        this.url = url;
        this.pengguna = pengguna;
        this.sandi = sandi;
        this.sambungan = null;
    }

    public Connection getSambungan() throws SQLException {
        if (sambungan == null || sambungan.isClosed()) {
            sambungan = DriverManager.getConnection(url, pengguna, sandi);
        }
        return sambungan;
    }

    public boolean koneksiAktif() {
        try {
            return getSambungan() != null && !getSambungan().isClosed();
        } catch (SQLException ex) {
            return false;
        }
    }
}