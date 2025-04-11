package vn.softdream.autotest.service;

import vn.softdream.autotest.dto.request.BaseRequestDTO;
import vn.softdream.autotest.dto.request.CreateAccountRequestDTO;
import vn.softdream.autotest.dto.request.UpdateAccountRequestDTO;
import vn.softdream.autotest.dto.request.UpdateAccountStatusRequestDTO;
import vn.softdream.autotest.dto.response.BaseResponseDTO;
import vn.softdream.autotest.entity.Account;

public interface AccountService {

    Account createNewAccount(CreateAccountRequestDTO request);

    BaseResponseDTO getAccountsWithPaging(BaseRequestDTO request);

    BaseResponseDTO getAccountDetail(Integer accountId);

    BaseResponseDTO updateAccount(UpdateAccountRequestDTO request);

    BaseResponseDTO updateAccountStatus(UpdateAccountStatusRequestDTO request);

    BaseResponseDTO deleteAccount(Integer accountId);
}
