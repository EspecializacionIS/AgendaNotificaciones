package com.clinic.agenda.api.dto;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class UserServiceResponse {
    private String username;
    private Boolean enabled;
    private Map<String, String> attributes;
    private List<String> groups;
}

