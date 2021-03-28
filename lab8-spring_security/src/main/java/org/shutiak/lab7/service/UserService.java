package org.shutiak.lab7.service;

import lombok.RequiredArgsConstructor;
import org.shutiak.lab7.dto.UserDto;
import org.shutiak.lab7.enums.Role;
import org.shutiak.lab7.model.BookEntity;
import org.shutiak.lab7.model.PermissionEntity;
import org.shutiak.lab7.model.UserEntity;
import org.shutiak.lab7.repository.RoleRepository;
import org.shutiak.lab7.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserEntity userEntity = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with login: " + username));
        return User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(userEntity.getPassword()))
                .authorities(mapAuthorities(userEntity.getRoleEntity().getPermissionEntities()))
                .build();
    }

    private static Set<GrantedAuthority> mapAuthorities(final Set<PermissionEntity> permissionEntities) {
        return permissionEntities.stream()
                .map(PermissionEntity::getPermission)
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableSet());
    }
    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities){
        return jwtTokenGenerator.generateToken(username,authorities);
    }
    @Transactional
    public UserEntity getUserByToken(final String token){
        return userRepository.findByLogin(jwtTokenGenerator.getUsernameFromToken(token)).orElse(null);
    }
    @Transactional
    public boolean existsByLogin(String login){
        return userRepository.existsByLogin(login);
    }

    @Transactional
    public UserEntity createNewUser(final UserDto newUser) {
        return userRepository.save(UserEntity.builder()
                .login(newUser.getLogin())
                .password((bCryptPasswordEncoder.encode(newUser.getPassword())))
                .roleEntity(roleRepository.findByRole(Role.USER))
                .build());
    }

    public Set<BookEntity> getFavourite(UserEntity user){
        return user.getFavouriteBookEntities();
    }

    @Transactional
    public void like(UserEntity userEntity, BookEntity bookEntity){
        Optional<UserEntity> userOptional = userRepository.findById(userEntity.getId());
        UserEntity user = userOptional.get();
        user.getFavouriteBookEntities().add(bookEntity);
        userRepository.saveAndFlush(userEntity);
    }
    @Transactional
    public void unlike(UserEntity userEntity, BookEntity bookEntity){
        Optional<UserEntity> userOptional = userRepository.findById(userEntity.getId());
        UserEntity user = userOptional.get();
        user.getFavouriteBookEntities().remove(bookEntity);
        userRepository.saveAndFlush(userEntity);
    }
}
