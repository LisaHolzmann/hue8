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

public class Producer implements Runnable {

    private final String name;
    private final Storage storage;
    private final int sleepTime;

    private final List<Integer> sent;
    private final int numberOfItems;

    public Producer(String name, Storage storage, int sleepTime, int numberOfItems) {
        this.name = name;
        this.storage = storage;
        this.sleepTime = sleepTime;
        this.numberOfItems = numberOfItems;
        this.sent = new ArrayList<>();

    }

    public List<Integer> getSent() {
        return sent;
    }

    @Override
    public void run() {
        for (Integer i = 0; i < numberOfItems; i++) {
            try {
                if (storage.put(i)) {
                    sent.add(i);
                } else {
                    do {
                        Thread.sleep(sleepTime);
                    } while (!storage.put(i));
                    sent.add(i);
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        storage.setProductionComplete();

    }

}
