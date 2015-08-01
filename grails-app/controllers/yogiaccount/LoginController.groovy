package yogiaccount

import com.system.Company
import com.login.LoginService
import com.system.RoleChild
import com.system.Screen
import com.system.SystemService
import com.system.User
import grails.converters.JSON
import org.codehaus.groovy.grails.commons.GrailsApplication


class LoginController {

    //static defaultAction = "auth"
    GrailsApplication grailsApplication
    LoginService loginService
    SystemService systemService
    def index() {
        render(view: "index");
    }

    def login() {
        println "Login Controller with action : login"
    }

    def auth = {
        def userInfo = loginService.authentication(params);
        if (userInfo) {
            if (userInfo.company) {
                session['activeUser'] = loginService.createSessionFindByUser(userInfo as User);
                session['company'] = loginService.getCompanyforSession(userInfo as User);
//              session['company'] = systemService.getCompanyObjectById(userInfo as User);
                session['roleRights'] = getRequestAuthenticate(userInfo.id);
                session['accountFeature'] = loginService.getAccountFeatureSettingByUser(userInfo as User);
                session['statutory'] = loginService.getStatutorySettingByUser(userInfo as User);
                session['verify'] = "single";
                redirect(controller: "login", action: "index");
            } else if (userInfo.organization) {
                def compList = systemService.findAllCompanyByOrganizationId(userInfo.organization.id as Long);
                session['activeUser'] = loginService.createSessionFindByUser(userInfo as User);
                session['company'] = "";
                if ((compList.size() > 0) && (userInfo.useMultipleCompany)) {
                    session['verify'] = "single";
//                    session['verify'] = "multiple";
                    redirect(controller: "login", action: "index");
                } else {
                    session['verify'] = "blank";
                    session['activeUser'] = loginService.createSessionFindByUser(userInfo as User);
                    session['company'] = "";
                    redirect(controller: "login", action: "index");
                }
                }
        } else {
            render(view: "/login/login", model: [msg: "Incorrect Username and Password"]);
            }
    }

    def getRequestAuthenticate(Long id) {
        return systemService.getDisplayScreenByRole(id);
    }

    def getScreenList = {
        def data = systemService.getDisplayScreenByRole(session['activeUser'].user.id as Long);
        if (data) {
            render data as JSON;
        } else {
            render '[]'
        }
    }

    def getVerify() {
        render session['verify'];
    }

    //bhupendra mahajan
    //due to checkbox status of multiple selection company i am create this fucntion to,
    //all company according to organization
    def findAllCompanyByOrgId() {
        def orgData = [];
        def data = systemService.findAllCompanyByOrganizationId(session['activeUser']?.organization?.id as Long);
        if (data) {
            data.each { c ->
                orgData.push([id: c.id, name: c.name, status: false])
            }
            render orgData as JSON;

        }
    }

    def createSession() {
        if (params) {
            int count = 0;
            def compId = "";
            def compList = [];
            def data = JSON.parse(params.list);
            data.each { c ->
                if (c.status) {
                    count++;
                    if (count == 1) {
                        compId = c.id;
                    }
                    compList.push(systemService.getCompanyById(c.id as Long));
                }
            }
            session['activeUser'].companyId = compList;
            if (count == 1) {
                session['verify'] = "single";
                session['company'] = systemService.getCompanySessionById(compId as Long);
            }
//           render "[]";
        }
        if (session['company']) {
            render session['company'] as JSON;
        } else {
            render "";
        }

    }

    def getUserPwd() {
        render systemService.getUserById(session['activeUser'].user.id as Long) as JSON;
    }

    def getComapnyList() {
        if (session['activeUser']?.companyId) {
            render session['activeUser']?.companyId as JSON;
        } else {
            render "[]";
        }
    }

    def getCompany() {
        if (session['company']) {
            render systemService.getCompanyById(session['company'].id as Long) as JSON;
        } else {
            render "";
        }

    }

    def checkUnique() {
        //version  : 1.0
        //owner    : bhupendra
        //date     : 25/08/2014
        //this code is dynamically find a domain class and return his property with yes or no
        //dynamic query for unique value against organization
        Class clazz = grailsApplication.domainClasses.find { it.clazz.simpleName == params.class }.clazz

        if (params?.id) {
            def d = clazz.find(params.query, [params.name as Long, session['company'] as Company])
            if (d) {
                render d as JSON
            } else {
                render "";
            }
        } else {
            def d = clazz.find(params.query, [params.name, session['company'] as Company])
            if (d) {
                render d as JSON
            } else {
                render "";
            }
        }
    }

    def logout() {
        session.invalidate()
        redirect(controller: 'login', action: 'login');
    }
}
