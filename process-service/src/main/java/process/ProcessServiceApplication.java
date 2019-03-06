package process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import process.messaging.input.DocumentInputChannel;
import process.messaging.output.DocumentOutputChannel;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding({ DocumentInputChannel.class, DocumentOutputChannel.class })
public class ProcessServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessServiceApplication.class, args);
    }


}
