package vn.iotstar.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.repository.UserInfoRepository;

import java.util.Optional;

public class UserInfoService implements UserDetailsService {
        UserInfoRepository repository;

        public UserInfoService(UserInfoRepository userInfoRepository) {
            this.repository = userInfoRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<UserInfo> userInfo = repository.findByName(username);
            return userInfo.map(UserInfoUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("user not found: " + username));
        }
    }

