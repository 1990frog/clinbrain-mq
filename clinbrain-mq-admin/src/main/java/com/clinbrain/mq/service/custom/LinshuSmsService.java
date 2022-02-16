package com.clinbrain.mq.service.custom;

import cn.hutool.core.util.StrUtil;
import com.clinbrain.mq.service.BaseSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  拼装参数,其实也用不上, 用 #P# 代表参数
 * Created by Liaopan on 2022-02-16.
 */
@Service("linshuService")
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "linshu")
public class LinshuSmsService extends BaseSmsService {

    @Override
    protected String parseTemplate(String templateContent, List<String> templateParams) {
        String result = templateContent;
        for(String param : templateParams) {
            result = templateContent.replaceFirst("#P#", param);
        }
        return result;
    }
}
