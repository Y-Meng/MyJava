package com.mingtai.base.task;

/**
 * @author zkzc-mcy create at 2017/12/5.
 */
public class TaskException extends Exception{

    public TaskException() {
    }

    public TaskException(String message) {
        super(message);
    }

    public TaskException(Throwable cause) {
        super(cause);
    }
}
