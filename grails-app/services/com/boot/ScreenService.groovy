package com.boot

import com.annotation.ParentScreen
import com.system.Screen
import grails.transaction.Transactional
import javassist.expr.Instanceof

@Transactional
class ScreenService {

    def grailsApplication

    def screen() {
        def result = Screen.list();
        if (!result) {

            def fullName, link;
            Integer sortList;
            def controllers = grailsApplication.controllerClasses.sort { it.fullName };
            List<Screen> screenList = new ArrayList<Screen>();
            for (scr in grailsApplication.controllerClasses.sort { it.fullName }) {

                if (scr.getClazz().isAnnotationPresent(ParentScreen)) {
                    if (scr.getClazz().getAnnotation(ParentScreen).fullName()) {
                        fullName = scr.getClazz().getAnnotation(ParentScreen).fullName();
                    } else {
                        fullName = "";
                    }

                    if (scr.getClazz().getAnnotation(ParentScreen)?.sortList()) {
                        sortList = scr.getClazz().getAnnotation(ParentScreen).sortList();
                    } else {
                        sortList = 0;
                    }
                    if (scr.getClazz().getAnnotation(ParentScreen)?.link()) {
                        link = scr.getClazz().getAnnotation(ParentScreen).link();
                    } else {
                        link = "";
                    }
                }


                Screen screen = new Screen(name: scr.fullName, controller: scr.logicalPropertyName, domainName: fullName, sortList: sortList, uniqueKey: 1, link: link)
                //domainName: screenName
                if (scr.getClazz().isAnnotationPresent(ParentScreen)) {
                    def parentScreenName = scr.getClazz().getAnnotation(ParentScreen).name();
                    def parentScreen = Screen.findByName(parentScreenName);
                    if (!parentScreen) {
                        parentScreen = new Screen(name: parentScreenName);
                        parentScreen.save();
                    }
                    screen.parentScreen = parentScreen;
                }
                if (screen.sortList instanceof Integer) screenList.add(screen);
            }
            Screen.saveAll(screenList);
        }
    }

}
