package org.example.openj916480;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class SimpleCodeGenerator {

    //Generate many Spring Configuration Classes
    public static void main(String[] args) {
        String classTemplate = """
        package org.example.openj16480.configuration;
                
                
        import lombok.Getter;
        import lombok.Setter;
        import org.springframework.boot.context.properties.ConfigurationProperties;
        import org.springframework.context.annotation.Configuration;
                
        @ConfigurationProperties(prefix = "demo.config%s")
        @Configuration
        @Getter
        @Setter
        public class Configuration%s {
          private String value;
        }
        """;

        IntStream.range(0, 10000).forEach(n -> {
            String classContent = String.format(classTemplate, n, n);

            try {
                Files.writeString(Path.of(
                                args[0])
                        .resolve("Configuration" + n + ".java"), classContent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}

