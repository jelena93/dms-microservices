package auth.service;

//@Component("userDetailsService")
public class UserDetailsService /*implements org.springframework.security.core.userdetails.UserDetailsService */{

//    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    @Transactional
//    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(final String login) {
//
//        log.debug("Authenticating {}", login);
//        String lowercaseLogin = login.toLowerCase();
//
//        User user = userRepository.findByUsernameCaseInsensitive(lowercaseLogin);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
//        }
//
//        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//                .map(a -> new SimpleGrantedAuthority(
//                        a.getName()))
//                .collect(Collectors.toList());
//
//        return new UserDetails(user.getUsername(), user.getPassword(),
//                user.getCompanyId(), true, grantedAuthorities);
//
//    }

}
