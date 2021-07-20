package com.clinbrain.mq.common.message;

import com.clinbrain.mq.common.enums.MessagesGenreEnum;
import lombok.Builder;

/**
 *
 * @author Liaopan
 * @date 2021-07-15
 * 短信信息
 */
public class SMSMessage extends BaseMessage {

    @Override
    public MessagesGenreEnum messageGenre() {
        return MessagesGenreEnum.SMS;
    }
}
