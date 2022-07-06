package com.clinbrain.mq.common.message;

import com.clinbrain.mq.common.enums.MessagesGenreEnum;

/**
 *
 * @author Liaopan
 * @date 2021-07-15
 */
public class EmailMessage extends BaseMessage {

    private String title;

    @Override
    public MessagesGenreEnum messageGenre() {
        return MessagesGenreEnum.EMAIL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
