package com.clinbrain.mq.service.custom;

import com.clinbrain.mq.service.BaseSmsService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by Liaopan on 2021-12-06.
 */
@Service("defaultService")
public class DefaultSmsService extends BaseSmsService {

    @Override
    protected String parseTemplate(String templateContent, List<String> templateParams) {
        return MessageFormat.format(templateContent, templateParams.toArray());
    }
}
