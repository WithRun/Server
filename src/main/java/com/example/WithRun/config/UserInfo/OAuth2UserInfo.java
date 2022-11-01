package com.example.WithRun.config.UserInfo;

public interface OAuth2UserInfo {

    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}
