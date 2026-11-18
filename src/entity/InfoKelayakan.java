package entity;

/**
 *
 * @author Maria
 */
public interface InfoKelayakan {
    
    //Menampilkan detail informasi khusus dari masing-masing alat
    void tampilkanDetailKhusus();
    
    //Mengecek status apakah kondisi alat sudah layak dan siap pakai
    boolean cekKondisiSiapPakai();
}
