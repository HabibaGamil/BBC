package com.example.test_app;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Properties {

    private String msg;
    private String buildVersion;
    private Map<String, String> cmdMap;
    private List<String> activeBranches;

    public Properties(String msg, Map<String, String> cmdMap) {
        this.msg = msg;
        this.cmdMap=cmdMap;

    }

}
