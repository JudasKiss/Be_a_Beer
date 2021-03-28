package com.cookandroid.beer;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class MemberInfo {
    private String name;

    public MemberInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
