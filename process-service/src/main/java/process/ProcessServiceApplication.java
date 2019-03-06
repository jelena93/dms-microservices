package process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import process.messaging.input.DocumentInputChannel;
import process.messaging.output.DocumentOutputChannel;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableSwagger2
@EnableBinding({ DocumentInputChannel.class, DocumentOutputChannel.class })
public class ProcessServiceApplication {
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2).select()
//                                                      .apis(RequestHandlerSelectors.basePackage("process.controller"))
//                                                      .paths(PathSelectors.any()).build();
//    }

    public static void main(String[] args) {
        SpringApplication.run(ProcessServiceApplication.class, args);
    }


}
