package filter

class SessionFilters {

    def filters = {
        all(controller:'*', action:'*') {
            before = {
                println "filter call"
                if(!request.xhr) return;
                if(!session['activeUser']){
                    println "redirect to Login page (login)"
                    redirect(controller: 'login' , action: "login")
                    return false
                }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
