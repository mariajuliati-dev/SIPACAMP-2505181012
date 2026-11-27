package entity;
import java.time.LocalDate;
/**
 *
 * @author Maria
 */
public interface TerperiksaBerkala {

    boolean perluDiperiksa();

    LocalDate batasPemeriksaan();

    default String statusPemeriksaan() {
        if (perluDiperiksa()) {
            return "PERLU DIPERIKSA sejak " + batasPemeriksaan();
        }
        return "LAYAK PAKAI sampai " + batasPemeriksaan();
    }
}
