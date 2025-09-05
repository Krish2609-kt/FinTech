package io.bootify.fin_tech.service;

import io.bootify.fin_tech.domain.Account;
import io.bootify.fin_tech.domain.KycDocument;
import io.bootify.fin_tech.domain.Role;
import io.bootify.fin_tech.domain.User;
import io.bootify.fin_tech.model.UserDTO;
import io.bootify.fin_tech.repos.AccountRepository;
import io.bootify.fin_tech.repos.KycDocumentRepository;
import io.bootify.fin_tech.repos.RoleRepository;
import io.bootify.fin_tech.repos.UserRepository;
import io.bootify.fin_tech.util.CustomCollectors;
import io.bootify.fin_tech.util.NotFoundException;
import io.bootify.fin_tech.util.ReferencedWarning;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final KycDocumentRepository kycDocumentRepository;

    public UserService(final UserRepository userRepository, final RoleRepository roleRepository,
            final AccountRepository accountRepository,
            final KycDocumentRepository kycDocumentRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.kycDocumentRepository = kycDocumentRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPasswordHash(user.getPasswordHash());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setFullName(user.getFullName());
        userDTO.setKycStatus(user.getKycStatus());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setUserRoleRoles(user.getUserRoleRoles().stream()
                .map(role -> role.getId())
                .toList());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setUsername(userDTO.getUsername());
        user.setPasswordHash(userDTO.getPasswordHash());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setFullName(userDTO.getFullName());
        user.setKycStatus(userDTO.getKycStatus());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setUpdatedAt(userDTO.getUpdatedAt());
        final List<Role> userRoleRoles = roleRepository.findAllById(
                userDTO.getUserRoleRoles() == null ? List.of() : userDTO.getUserRoleRoles());
        if (userRoleRoles.size() != (userDTO.getUserRoleRoles() == null ? 0 : userDTO.getUserRoleRoles().size())) {
            throw new NotFoundException("one of userRoleRoles not found");
        }
        user.setUserRoleRoles(new HashSet<>(userRoleRoles));
        return user;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Account userAccount = accountRepository.findFirstByUser(user);
        if (userAccount != null) {
            referencedWarning.setKey("user.account.user.referenced");
            referencedWarning.addParam(userAccount.getId());
            return referencedWarning;
        }
        final KycDocument userKycDocument = kycDocumentRepository.findFirstByUser(user);
        if (userKycDocument != null) {
            referencedWarning.setKey("user.kycDocument.user.referenced");
            referencedWarning.addParam(userKycDocument.getId());
            return referencedWarning;
        }
        return null;
    }

    public Map<Long, String> getUserValues() {
        return userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getUsername));
    }

}
