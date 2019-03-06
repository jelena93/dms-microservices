package company;

import company.messaging.UserOutputChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableSwagger2
@EnableBinding({ UserOutputChannel.class })
public class CompanyServiceApplication {

    //    @Bean
    //    public Docket api() {
    //        return new Docket(DocumentationType.SWAGGER_2).select()
    //                .apis(RequestHandlerSelectors.basePackage("company.controller"))
    //                .paths(PathSelectors.any()).build();
    //    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(CompanyServiceApplication.class, args);
    }

}
