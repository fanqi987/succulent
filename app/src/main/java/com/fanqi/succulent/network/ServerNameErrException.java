package com.fanqi.succulent.network;

public class ServerNameErrException extends Exception {

    private static final String message = "填写服务器基名称错误";

    public ServerNameErrException() {
        super(message);
    }
}
