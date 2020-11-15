package ejercicio13;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Phaser;


public class CiclistasPhaser extends Phaser {

    public static final int ARRIVE_TO_GAS_STATION_PHASE = 0;
    public static final int COME_VENTA_PHASE = 1;
    public static final int FINISH_PHASE = 2;
    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm:ss");



    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case ARRIVE_TO_GAS_STATION_PHASE:
                System.out.printf("%s - Comienza la etapa (ejecutado en %s)\n",
                        LocalTime.now().format(dateTimeFormatter),
                        Thread.currentThread().getName());
                break;
            case COME_VENTA_PHASE:
                System.out.printf("%s - De vuelta a casa,(ejecutado en %s)\n",
                        LocalTime.now().format(dateTimeFormatter),
                        Thread.currentThread().getName());
                break;
            case FINISH_PHASE:
                System.out.printf("%s - Etapa finalizada,(ejecutado en %s)\n",
                        LocalTime.now().format(dateTimeFormatter),
                        Thread.currentThread().getName());

                return true;
        }
        return super.onAdvance(phase, registeredParties);
    }
}
