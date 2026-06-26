package org.xbs.shared.application;

public record Response<T>(T data, String error) {

    public static <T> Response<T> ok(T data) {
        return new Response<>(data, null);
    }

    public static <T> Response<T> error(String error) {
        return new Response<>(null, error);
    }

    public boolean isSuccess() {
        return error == null;
    }
}
