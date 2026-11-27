package layanan;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
/**
 *
 * @author Maria
 */
public class PemantauPersewaan implements Runnable {

    private final long jedaMilidetik;
    private final Consumer<String> pelaporStatus;
    private final AtomicBoolean aktif = new AtomicBoolean(false);
    private Thread workerThread;

    public PemantauPersewaan(long jedaMilidetik, Consumer<String> pelaporStatus) {
        this.jedaMilidetik = Math.max(2000L, jedaMilidetik);
        this.pelaporStatus = pelaporStatus;
    }

    public void mulai() {
        if (aktif.get()) {
            return;
        }
        aktif.set(true);
        workerThread = new Thread(this, "sipacamp-pemantau-persewaan");
        workerThread.setDaemon(true);
        workerThread.start();
    }

    public void hentikan() {
        aktif.set(false);
        if (workerThread != null) {
            workerThread.interrupt();
        }
    }

    @Override
    public void run() {
        while (aktif.get()) {
            try {
                Thread.sleep(jedaMilidetik);
                if (!aktif.get()) {
                    break;
                }
                if (pelaporStatus != null) {
                    pelaporStatus.accept("Pemantauan berkala aktif: sinkronisasi status perlengkapan siap.");
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
