package org.server.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public 
enum OrderStatus {
    NORMAL("正常"), DISCARD("报废");

    private String status;
    private OrderStatus(String status) {
        if (validate(status)) {
            this.status = status;
        }
        else {
            // 随便搬来的，意思不大一样，记得换一个，比方EnumValidateException
            throw new EnumConstantNotPresentException(OrderStatus.class, status);
        }
    }

    public static Boolean validate(String status) {
        if (status.equals("正常") || status.equals("报废")) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setStatus(String status) {
        if (validate(status)) {
            this.status = status;
        }
        else {
            throw new EnumConstantNotPresentException(OrderStatus.class, status);
        }
    }
    @JsonValue
    public String getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return status;
    }
}
