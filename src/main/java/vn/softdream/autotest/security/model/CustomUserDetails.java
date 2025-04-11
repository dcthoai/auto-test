package vn.softdream.autotest.security.model;

import vn.softdream.autotest.constants.AccountConstants;
import vn.softdream.autotest.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private final Account account;

    private CustomUserDetails(Account account,
                              Collection<? extends GrantedAuthority> authorities,
                              boolean accountEnabled,
                              boolean accountNonExpired,
                              boolean credentialsNonExpired,
                              boolean accountNonLocked) {
        super(
            account.getUsername(),
            account.getPassword(),
            accountEnabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            authorities
        );

        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public static Builder customBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Account account;
        private Collection<? extends GrantedAuthority> authorities;
        private boolean accountEnabled = true;
        private boolean accountNonExpired = true;
        private boolean accountNonLocked = true;

        public Builder account(Account account) {
            this.account = account;

            switch (account.getStatus()) {
                case AccountConstants.STATUS.ACTIVE -> accountEnabled = true;
                case AccountConstants.STATUS.INACTIVE -> accountEnabled = false;
                case AccountConstants.STATUS.LOCKED -> accountNonLocked = false;
                case AccountConstants.STATUS.DELETED -> accountNonExpired = false;
            }

            return this;
        }

        public Builder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public CustomUserDetails build() {
            boolean credentialsNonExpired = true;

            return new CustomUserDetails(
                account,
                authorities,
                accountEnabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked
            );
        }
    }
}
