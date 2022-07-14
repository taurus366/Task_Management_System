package com.system.management.exception;

public class NotOwnerException extends RuntimeException {

    private static String reason;

    public NotOwnerException(String reason) {
        super(setReason(reason));
    }

    public static String setReason(String reason2) {
        if (reason2.length() == 0){
            reason = "Only Owner has privileges to use this feature";

        }else {
            reason = reason2;
        }
        return reason;
    }
}
