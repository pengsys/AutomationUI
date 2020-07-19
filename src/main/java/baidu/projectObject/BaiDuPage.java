package baidu.projectObject;

import baidu.projectElement.BaiDuEle;
import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * @author peng_luo@yeah.net
 * @version 1.0
 * @date 2020/7/2 22:31
 */
public class BaiDuPage extends BaiDuEle {
    public BaiDuPage bai_du_click(String text){
        $(text_box).should(Condition.exist).clear();
        $(text_box).sendKeys(text);
        $(input_submit).should(Condition.exist).click();
        return page(BaiDuPage.class);
    }
    public BaiDuPage BaiDuClick(String text){
        bai_du_click(text);
        return this;
    }
}
