import com.boot.*

class BootStrap {


    def screenService
    def dataFeedService
    def init = { servletContext ->
        screenService.screen();
        dataFeedService.dataFeedTaxType();
        dataFeedService.settingEntry();
        dataFeedService.dataFeedGroup();
        dataFeedService.dataFeedLedger();

        dataFeedService.accountSetting();
    }
    def destroy = {
    }
}
