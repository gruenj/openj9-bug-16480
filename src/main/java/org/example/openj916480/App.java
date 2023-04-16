package org.example.openj916480;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class App {

    @EventListener(ApplicationReadyEvent.class)
    public void print() {
        System.out.println("Application Started..");
    }

}