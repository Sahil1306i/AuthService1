package org.example.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.example.entities.UserInfo;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
//user_name -> userName
public class UserInfoDto extends UserInfo
{
    private String userName;

    private String lastName;

    private Long phoneNumber;

    private String email;
}
