package document;

import document.messaging.DocumentInputChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding({DocumentInputChannel.class})
public class DocumentServiceApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DocumentServiceApplication.class, args);
    }
}
