package com.common

class VoucherStatus {
    String voucherProperty
    String parentLabel, parentStatus
    String childLabel, childStatus

    static constraints = {
        voucherProperty nullable: true
        parentLabel nullable: true
        parentStatus nullable: true
        childLabel nullable: true
        childStatus nullable: true
    }
}
