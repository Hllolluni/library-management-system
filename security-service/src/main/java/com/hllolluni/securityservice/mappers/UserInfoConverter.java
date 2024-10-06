package com.hllolluni.securityservice.mappers;

import com.hllolluni.securityservice.entities.UserInfo;
import com.hllolluni.securityservice.models.CustomUserDetails;
import com.hllolluni.securityservice.models.UserInfoTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserInfoConverter {
     UserInfo convertToUserInfo(UserInfoTransfer userInfoTransfer);
     @Mapping(target = "password", ignore = true)
     UserInfoTransfer convertToUserInfoTransfer(UserInfo userInfo);
     UserInfoTransfer convertToUserInfoTransferFromUserDetails(CustomUserDetails customUserDetails);

}
