package com.clinbrain.mq.common.message;

import com.clinbrain.mq.common.enums.MessagesGenreEnum;

/**
 *
 * @author Liaopan
 * @date 2021-07-15
 */
public class EmailMessage extends BaseMessage {

    @Override
    public MessagesGenreEnum messageGenre() {
        return MessagesGenreEnum.EMAIL;
    }
}
