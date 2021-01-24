package baidu;

import com.codeborne.selenide.Configuration;
import common.until.datautil.PropertiesHandler;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.open;

/**
 * @author peng_luo@yeah.net
 * @version 1.0
 * @date 2020/7/11 14:12
 */
public class Initialization {
    @BeforeSuite
    public void beforeSuite() throws IOException {
        PropertiesHandler.loadPropertiesByProjectEnv("SIT");
        System.setProperty("webdriver.chrome.driver", "F:\\Code\\grid\\chromedriver.exe");
        Configuration.browser = "chrome";
//        Configuration.savePageSource = false;
//        Configuration.screenshots = true;
//        Configuration.clickViaJs = true;
        Configuration.startMaximized = true;
        Configuration.headless = false;
//        Configuration.remote ="http://localHost:4444/wd/hub";
        Configuration.timeout = 2000;

        //修改报告默认生成路径
//        Configuration.reportsFolder = "target/reports/test";

//        //设置chrome手机浏览界面
//        Map<String, String> mobileEmulation = new HashMap<String, String>();
//        //设置设备,例如:Google Nexus 7/Apple iPhone 6
//        //mobileEmulation.put("deviceName", "Google Nexus 7");
//        mobileEmulation.put("deviceName", "Apple iPhone 6 Plus");   //这里是要使用的模拟器名称，就是浏览器中模拟器中的顶部型号
//        Map<String, Object> chromeOptions = new HashMap<String, Object>();
//        chromeOptions.put("mobileEmulation", mobileEmulation);
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
    }
    @BeforeTest
    public void beforeTest() {
        open("http://www.baidu.com");
    }
}
