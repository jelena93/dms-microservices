package auth.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
//@EnableWebSecurity
@Order(-20)
public class WebSecurityConfiguration /*extends WebSecurityConfigurerAdapter */{
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new StandardPasswordEncoder();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin().loginPage("/login").permitAll().and().logout().and().requestMatchers()
//                .antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access").
//                and().authorizeRequests().anyRequest().authenticated();
//    }
}

