package vn.softdream.autotest.service.impl;

import vn.softdream.autotest.common.Common;
import vn.softdream.autotest.constants.AccountConstants;
import vn.softdream.autotest.constants.ExceptionConstants;
import vn.softdream.autotest.dto.auth.AccountDTO;
import vn.softdream.autotest.dto.mapping.IAccountDTO;
import vn.softdream.autotest.dto.mapping.IRoleDTO;
import vn.softdream.autotest.dto.request.BaseRequestDTO;
import vn.softdream.autotest.dto.request.CreateAccountRequestDTO;
import vn.softdream.autotest.dto.request.UpdateAccountRequestDTO;
import vn.softdream.autotest.dto.request.UpdateAccountStatusRequestDTO;
import vn.softdream.autotest.dto.response.BaseResponseDTO;
import vn.softdream.autotest.entity.Account;
import vn.softdream.autotest.entity.AccountRole;
import vn.softdream.autotest.entity.Role;
import vn.softdream.autotest.exception.BaseBadRequestException;
import vn.softdream.autotest.repositories.AccountRepository;
import vn.softdream.autotest.repositories.AccountRoleRepository;
import vn.softdream.autotest.repositories.RoleRepository;
import vn.softdream.autotest.service.AccountService;
import vn.softdream.autotest.service.RoleService;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private static final String ENTITY_NAME = "AccountServiceImpl";
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final AccountRoleRepository accountRoleRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              PasswordEncoder passwordEncoder,
                              RoleService roleService,
                              RoleRepository roleRepository,
                              AccountRoleRepository accountRoleRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.accountRoleRepository = accountRoleRepository;
    }

    @Override
    public BaseResponseDTO getAccountsWithPaging(BaseRequestDTO request) {
        if (request.getPageable().isPaged()) {
            Page<IAccountDTO> accountsWithPaged = accountRepository.findAllWithPaging(request.getPageable());
            List<IAccountDTO> accounts = accountsWithPaged.getContent();
            return BaseResponseDTO.builder().total(accountsWithPaged.getTotalElements()).ok(accounts);
        }

        return BaseResponseDTO.builder().ok(accountRepository.findAllNonPaging());
    }

    @Override
    public BaseResponseDTO getAccountDetail(Integer accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.ACCOUNT_NOT_EXISTED);
        }

        Account account = accountOptional.get();
        AccountDTO accountDTO = new AccountDTO();
        BeanUtils.copyProperties(account, accountDTO);
        Common.setAuditingInfo(account, accountDTO);
        accountDTO.setAccountRoles(roleService.getAccountRoles(accountId));

        return BaseResponseDTO.builder().ok(accountDTO);
    }

    @Override
    @Transactional
    public Account createNewAccount(CreateAccountRequestDTO request) {
        boolean isExistedAccount = accountRepository.existsByUsernameOrEmail(request.getUsername(), request.getEmail());

        if (isExistedAccount) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.ACCOUNT_EXISTED);
        }

        List<IRoleDTO> roles = roleRepository.findAllByIds(request.getRoleIds());

        if (roles.isEmpty() || roles.size() != request.getRoleIds().size()) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.ROLE_PERMISSION_INVALID);
        }

        String rawPassword = request.getPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);

        Account account = new Account();
        BeanUtils.copyProperties(request, account);
        account.setPassword(hashedPassword);
        account.setStatus(AccountConstants.STATUS.ACTIVE);
        accountRepository.save(account);

        List<AccountRole> accountRoles = new ArrayList<>();
        roles.forEach(role -> accountRoles.add(new AccountRole(account.getId(), role.getId())));
        accountRoleRepository.saveAll(accountRoles);

        return account;
    }

    @Override
    @Transactional
    public BaseResponseDTO updateAccount(UpdateAccountRequestDTO request) {
        Long existedAccounts = accountRepository.countByUsernameOrEmailAndIdNot(
            request.getUsername(),
            request.getEmail(),
            request.getId()
        );

        if (existedAccounts > 0) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.ACCOUNT_EXISTED);
        }

        Optional<Account> accountOptional = accountRepository.findById(request.getId());

        if (accountOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.ACCOUNT_NOT_EXISTED);
        }

        List<Role> accountRolesForUpdate = roleRepository.findAllById(request.getRoleIds());

        if (accountRolesForUpdate.isEmpty() || accountRolesForUpdate.size() != request.getRoleIds().size()) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.ROLE_PERMISSION_INVALID);
        }

        Account account = accountOptional.get();
        BeanUtils.copyProperties(request, account);
        account.setRoles(accountRolesForUpdate);
        accountRepository.save(account);

        return BaseResponseDTO.builder().ok();
    }

    @Override
    @Transactional
    public BaseResponseDTO updateAccountStatus(UpdateAccountStatusRequestDTO request) {
        accountRepository.updateAccountStatusById(request.getAccountId(), request.getStatus());
        return BaseResponseDTO.builder().ok();
    }

    @Override
    @Transactional
    public BaseResponseDTO deleteAccount(Integer accountId) {
        if (Objects.isNull(accountId) || accountId <= 0) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.INVALID_REQUEST_DATA);
        }

        accountRepository.updateAccountStatusById(accountId, AccountConstants.STATUS.DELETED);
        return BaseResponseDTO.builder().ok();
    }
}
