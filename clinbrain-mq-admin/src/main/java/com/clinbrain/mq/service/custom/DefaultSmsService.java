package com.clinbrain.mq.service.custom;

import com.clinbrain.mq.service.BaseSmsService;
import com.clinbrain.mq.service.ISmsTemplateService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by Liaopan on 2021-12-06.
 */
public class DefaultSmsService extends BaseSmsService {

    @Override
    protected String parseTemplate(String templateContent, List<String> templateParams) {
        return MessageFormat.format(templateContent, templateParams.toArray());
    }
}
