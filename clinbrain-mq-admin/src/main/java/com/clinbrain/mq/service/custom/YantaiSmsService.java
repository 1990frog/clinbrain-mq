package com.clinbrain.mq.service.custom;

import com.clinbrain.mq.service.BaseSmsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Liaopan on 2021-12-06.
 */
@Service("yantaiService")
public class YantaiSmsService extends BaseSmsService {
    private final static Pattern PATTERN = Pattern.compile("(\\{[x]*\\})");
    @Override
    protected String parseTemplate(String templateContent, List<String> templateParams) {
        // 短信模板：报警服务器地址{xxxxxxxxxxxxxx}报警类型{xxxxxxxxxx}报警时间{xxxxxxxxxxxxxxxxxxxx}报警内容进程{xxxxxxxxxxxxxxxxxxxx}
        Matcher matcher = PATTERN.matcher(templateContent);
        int group = 0;
        while (matcher.find()) {
            String item = matcher.group();
            //去掉前后{}
            item = item.substring(1, item.length() - 1);
            String target = templateParams.get(group);
            // 超过模板定义的部分截断，不足模板长度用空格追加
            if (item.length() > target.length()) {
                for (int i = 0; i < item.length() - target.length(); i++) {
                    target += " ";
                }
            } else {
                target = target.substring(0, item.length());
            }
            templateContent = templateContent.replaceFirst(item, target);
            group++;
        }
        return templateContent.replaceAll("\\{", "").replaceAll("}", "");
    }
}
