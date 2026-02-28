package com.duong.RestaurantManagement.exception;

public class ErrorApiResponse {
        private int status;
        private String message;

        public ErrorApiResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }
}
