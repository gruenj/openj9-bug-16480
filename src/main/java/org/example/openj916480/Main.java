package org.example.openj916480;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = {"org.example.openj916480.configuration"})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // entrypoint for native agent thread
    public static void agentMethodeEntry() throws Exception {
        String out = generateRandomData();
        System.out.println(out.substring(10, 100));
    }

    // generate random data
    public static String generateRandomData() throws Exception {
        System.out.println("generateRandomData Thread:" + Thread.currentThread().getId());
        List<String> data = new ArrayList<>(50000000);

        IntStream.range(0, 50000000).forEach(random -> {
            data.add("" + ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));
        });
        return String.join(",", data);
    }

}