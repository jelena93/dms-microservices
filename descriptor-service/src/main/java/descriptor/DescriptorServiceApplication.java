package descriptor;

import descriptor.messaging.input.DocumentInputChannel;
import descriptor.messaging.output.DocumentOutputChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableSwagger2
@EnableBinding({DocumentOutputChannel.class, DocumentInputChannel.class})
//@EnableCircuitBreaker
//@EnableOAuth2Client
public class DescriptorServiceApplication {

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors
//                .basePackage("descriptor.controller"))
//                .paths(PathSelectors.any()).build();
//    }

    @LoadBalanced
    @Bean
    public RestTemplate auth2RestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(DescriptorServiceApplication.class, args);
    }
}
