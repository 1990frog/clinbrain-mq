package com.clinbrain.mq.service.custom;

import cn.hutool.core.util.ReUtil;
import com.clinbrain.mq.service.BaseSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuehl
 * @date 2022-02-24
 */
@Service("wenzhou2Service")
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "wenzhou2")
public class Wenzhou2SmsService extends BaseSmsService {

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
