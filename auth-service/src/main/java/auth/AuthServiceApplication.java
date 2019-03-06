package auth;

import auth.messaging.UserInputChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableResourceServer
@Controller
@SessionAttributes("authorizationRequest")
@EnableBinding({UserInputChannel.class})
public class AuthServiceApplication extends WebMvcConfigurerAdapter {

//    @RequestMapping("/user")
//    @ResponseBody
//    public Authentication user(Authentication user) {
//        return user;
//    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("login");
//    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
