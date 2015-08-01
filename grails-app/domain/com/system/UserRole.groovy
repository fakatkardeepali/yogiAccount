package com.system

class UserRole {

    User user
    Role role
    Integer uniqueKey = 0

    Date lastUpdated, dateCreated
    Organization organization
    Company company

    static constraints = {
        organization nullable: true
        company nullable: true
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
