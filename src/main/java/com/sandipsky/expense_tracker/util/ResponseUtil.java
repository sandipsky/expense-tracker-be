package com.sandipsky.expense_tracker.util;
import com.sandipsky.expense_tracker.dto.ApiResponse;

public class ResponseUtil {

    public static <T> ApiResponse<T> success(int dataId, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMessage(message);
        response.setDataId(dataId);
        return response;
    }

    public static <T> ApiResponse<T> error(String message, int errorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMessage(message);
        response.setErrorCode(errorCode);
        return response;
    }
}
