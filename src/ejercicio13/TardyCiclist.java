package ejercicio13;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class TardyCiclist implements Runnable {
    private Phaser phaser;
    private final Random random = new Random();
    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public TardyCiclist(Phaser phaser) {
        this.phaser = phaser;
    }


    @Override
    public void run() {
        if (!phaser.isTerminated()) {
            int join = phaser.register();
            leaveHome();
            try {
                arriveGasStation();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (join <= CiclistasPhaser.ARRIVE_TO_GAS_STATION_PHASE) {
                try {
                    phaser.awaitAdvanceInterruptibly(phaser.arrive());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            try {
                arriveVenta();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (join <= CiclistasPhaser.COME_VENTA_PHASE) {
                try {
                    phaser.awaitAdvanceInterruptibly(phaser.arrive());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }

            //
            try {
                comeBackGasStation();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (join <= CiclistasPhaser.FINISH_PHASE) {
                try {
                    phaser.awaitAdvanceInterruptibly(phaser.arrive());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            try {
                comeBackHome();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                atHome();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.printf("%s - %s se desperto demasiado tarde\n",
                    LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());
        }


    }


    private void leaveHome() {
        System.out.printf("%s - El %s abadona su casa y se dirige hacia la gasolinera\n", LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());
    }

    private void arriveGasStation() throws InterruptedException {
        TimeUnit.SECONDS.sleep(random.nextInt(3) + 1);
        System.out.printf("%s - El %s llega a la gasolinera\n", LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());
    }

    private void arriveVenta() throws InterruptedException {
        TimeUnit.SECONDS.sleep(random.nextInt(10) + 5);
        System.out.printf("%s - El %s llega a la venta\n", LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());
    }

    private void comeBackGasStation() throws InterruptedException {
        TimeUnit.SECONDS.sleep(random.nextInt(10) + 5);
        System.out.printf("%s - El %s vuelve a la gasolinera desde la venta \n", LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());
    }

    private void comeBackHome() throws InterruptedException {
        TimeUnit.SECONDS.sleep(random.nextInt(3) + 1);
        System.out.printf("%s - El %s vuelva a casa desde la gasolinera\n", LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());
    }

    private void atHome() throws InterruptedException {

        System.out.printf("%s - El %s ya esta en casa\n", LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());
    }

}