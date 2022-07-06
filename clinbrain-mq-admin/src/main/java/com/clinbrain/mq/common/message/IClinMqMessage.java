package com.clinbrain.mq.common.message;

import com.clinbrain.mq.common.enums.MessagesGenreEnum;

/**
 * Created by Liaopan on 2021-07-20.
 */
public interface IClinMqMessage {

    /**
     * 消息类型
     * @return
     */
    MessagesGenreEnum messageGenre();
}
