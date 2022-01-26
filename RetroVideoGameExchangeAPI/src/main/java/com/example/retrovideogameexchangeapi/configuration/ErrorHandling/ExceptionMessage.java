package com.example.retrovideogameexchangeapi.configuration.ErrorHandling;

public class ExceptionMessage {

    private String _message;
    private Integer _httpStatus;

    public ExceptionMessage(String message, Integer httpStatus) {
        this._message = message;
        this._httpStatus = httpStatus;
    }

    public String get_message() {
        return _message;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public Integer get_httpStatus() {
        return _httpStatus;
    }

    public void set_httpStatus(Integer _httpStatus) {
        this._httpStatus = _httpStatus;
    }
}
