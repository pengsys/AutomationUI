package baidu.projectElement;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selectors.byId;

/**
 * @author peng_luo@yeah.net
 * @version 1.0
 * @date 2020/7/2 22:12
 */
public class BaiDuEle {

    public static By text_box = byId("kw");
    public static By input_submit = byId("su");
}
