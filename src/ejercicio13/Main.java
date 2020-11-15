package ejercicio13;


import java.util.concurrent.TimeUnit;

public class Main {
    private static final int NUMBER_OF_CYCLIST = 10;

    public static void main(String[] args) throws InterruptedException {

        CiclistasPhaser ciclistasPhaser= new CiclistasPhaser();

        for (int i = 0; i < NUMBER_OF_CYCLIST; i++) {
            new Thread(new Ciclistas(ciclistasPhaser),"ciclista "+(i+1)).start();

        }
        new Thread(new impacientCiclista(ciclistasPhaser),"ciclista impaciente").start();
        TimeUnit.SECONDS.sleep(10);
        new Thread(new TardyCiclist(ciclistasPhaser),"ciclista medio dormilon").start();
        TimeUnit.SECONDS.sleep(25);
        new Thread(new TardyCiclist(ciclistasPhaser),"ciclista dormilon").start();

    }
}
