package hotel.hotel_spring.security.details;

import hotel.hotel_spring.member.domain.Member;
import hotel.hotel_spring.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("해당 이메일로 찾을 수 없습니다. == > " + email));

        return new CustomUserDetails(member);
    }
}
