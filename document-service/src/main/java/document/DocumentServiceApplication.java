package document;

import document.messaging.DocumentInputChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableSwagger2
@EnableBinding({DocumentInputChannel.class})
public class DocumentServiceApplication {

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2).select()
//                .apis(RequestHandlerSelectors.basePackage("document.controller"))
//                .paths(PathSelectors.any()).build();
//    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DocumentServiceApplication.class, args);
    }
}
