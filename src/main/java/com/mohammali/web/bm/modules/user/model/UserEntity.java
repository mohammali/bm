package com.mohammali.web.bm.modules.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long id;

    private String name;

    private String username;

    private String password;

    private List<String> authority;
}
