package com.clinbrain.mq.service.custom;

import cn.hutool.core.util.ReUtil;
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
        // 验证参数
        final List<String> paramSize = ReUtil.findAll("#P#", templateContent, 0);
        if(paramSize.size() != templateParams.size()) {
            throw new IllegalArgumentException("参数个数与模板不符合");
        }
        String result = templateContent;
        for(String param : templateParams) {
            result = result.replaceFirst("#P#", param);
        }
        return result;
    }
}
