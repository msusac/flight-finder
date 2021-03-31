package hr.tvz.susac.java.web.flight.security;

import hr.tvz.susac.java.web.flight.model.User;
import hr.tvz.susac.java.web.flight.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsImpl implements UserDetailsService
{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        final User user = userRepository.findOneByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exists!"));

        return new UserDetails(user);
    }
}
