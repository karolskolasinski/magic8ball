//package pl.karolskolasinski.magic8ball.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import pl.karolskolasinski.bquizgame.model.account.Account;
//import pl.karolskolasinski.bquizgame.model.account.AccountRole;
//import pl.karolskolasinski.bquizgame.repository.AccountRepository;
//
//import java.util.Optional;
//
//@Service
//public class AuthenticationService implements UserDetailsService {
//
//    private final AccountRepository accountRepository;
//
//    @Autowired
//    public AuthenticationService(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
//        if (optionalAccount.isPresent()) {
//            Account account = optionalAccount.get();
//            String[] roles = account.getAccountRoles()
//                    .stream()
//                    .map(AccountRole::getName).toArray(String[]::new);
//            return User.builder()
//                    .username(account.getUsername())
//                    .password(account.getPassword())
//                    .roles(roles)
//                    .build();
//        }
//        throw new UsernameNotFoundException("Username not found.");
//    }
//
//}
