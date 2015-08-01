package com.login


import com.common.SettingService
import com.system.RoleChild
import com.system.SystemService
import com.system.User
import com.system.UserRole

//import grails.compiler.GrailsCompileStatic
import grails.transaction.Transactional

// Edited by Akshay Pingle
// Example    method name =  methodType + domainName + "By" + propertyName

//@GrailsCompileStatic
@Transactional
class LoginService {

    def SystemService systemService
    def SettingService settingService


    def authentication(Map para) {
        if (para) {
            String username = para.j_username;
            String password = para.j_password;
            def user = systemService.getUserByUsernameAndPassword(username, password);
            if (user) {
                return user;
            } else {
                return false;
            }
        }
    }

    def createSessionFindByUser(User user) {
        def ses;
        ses = [
                organization: user.organization,
                companyId: user?.company?.id ? systemService.findAllCompanyByOrganizationId(user?.organization?.id as Long) : "",
                username: user?.username,
                password: user?.password,
                user    : user

        ]
        return ses;
    }


    def getCompanyInfo(User user) {
        if (user?.company) {
            return systemService.getCompanyById(user.company.id);
        } else {

            return null;
        }
    }

    def getAccountFeatureSettingByUser(User user) {
        if (user?.company) {
            return settingService.getAccountfeatureByCompany(user.company);
        } else {
            return null;
        }
    }

    def getStatutorySettingByUser(User user) {

        if (user?.company) {
            return settingService.getStatutoryByCompany(user.company);
        } else {
            return null;
        }
    }

    def getCompanyforSession(User user) {
        if (user?.company) {
            return user.company;
        } else {
            return null;
        }
    }

    def getUserRoleRightsByUser(User user) {
        def userRole = RoleChild.findByRole(getUserRole(user))
        def child = [];
        if (userRole.size() > 0) {
            userRole.each { c ->
                child.push(path: c.screen.link,
                        canAdd: c.canAdd,
                        canUpdate: c.canUpdate,
                        canDelete: c.canDelete,
                        canPrint: c.canPrint)
            }
        }
        if (child) {
            return child
        } else {
            return []
        }
    }

    def getUserRole(User user) {
        return UserRole.findByUser(user)
    }
}
