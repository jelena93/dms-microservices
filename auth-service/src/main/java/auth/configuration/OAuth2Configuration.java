package auth.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableAuthorizationServer
public class OAuth2Configuration /*extends AuthorizationServerConfigurerAdapter */{
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private DataSource dataSource;
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    public JdbcTokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource);
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints
//                .authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService)
//                .tokenStore(tokenStore());
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("api-gateway")
//                .secret("api-gateway-secret")
//                .authorizedGrantTypes("authorization_code", "refresh_token")
//                .scopes("read", "write")
//                .autoApprove(true);
//    }
}
