package vn.softdream.autotest.service;

import vn.softdream.autotest.dto.mapping.IRoleDTO;
import vn.softdream.autotest.dto.request.BaseRequestDTO;
import vn.softdream.autotest.dto.request.CreateRoleRequestDTO;
import vn.softdream.autotest.dto.request.UpdateRoleRequestDTO;
import vn.softdream.autotest.dto.response.BaseResponseDTO;

import java.util.List;

public interface RoleService {

    BaseResponseDTO getRolesWithPaging(BaseRequestDTO request);
    BaseResponseDTO getRoleDetail(Integer roleId);
    BaseResponseDTO getPermissionTree();
    BaseResponseDTO createNewRole(CreateRoleRequestDTO request);
    BaseResponseDTO updateRole(UpdateRoleRequestDTO request);
    BaseResponseDTO deleteRole(Integer roleId);
    List<IRoleDTO> getAccountRoles(Integer accountId);
}
