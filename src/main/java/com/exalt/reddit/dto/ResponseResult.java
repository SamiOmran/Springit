package com.exalt.reddit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class ResponseResult {

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private  int status;

}
