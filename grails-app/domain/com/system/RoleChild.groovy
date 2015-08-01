package com.system

class RoleChild {

    Screen screen
    Screen parentScreen
    boolean canAdd = false
    boolean canUpdate = false
    boolean canDelete = false
    boolean canPrint = false
    boolean canView = false
    boolean status = false
    Company company
    Integer uniqueKey = 0

    static belongsTo = [role: Role]

    static constraints = {
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
