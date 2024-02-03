package com.chatapp.backend.Util;

import lombok.Data;

@Data
public class SuccessResponse<T> {
    private int status;
    private String description;
    private T data;

}
