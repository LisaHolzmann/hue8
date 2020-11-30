/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hue8;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {

    private final String name;
    private final Storage storage;
    private final int sleepTime;

    private final List<Integer> received;
    private boolean running;

    public Consumer(String name, Storage storage, int sleepTime) {
        this.name = name;
        this.storage = storage;
        this.running = true;
        this.sleepTime = sleepTime;
        this.received = new ArrayList<>();
    }

    public List<Integer> getReceived() {
        return received;
    }

    @Override
    public void run() {
        while ((!storage.isProductionComplete()) || (running == true)) {
            Integer temp = storage.get();
            if (temp != null) {
                received.add(temp);
                running = true;
            } else {
                running = false;
            }

            try {
                Thread.sleep(sleepTime);

            } catch (InterruptedException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
