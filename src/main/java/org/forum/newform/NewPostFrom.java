package org.forum.newform;

import javax.validation.constraints.Size;

public class NewPostFrom {

    @Size(min = 3, message = "{Size.Post.content.validation}")
    private String content;

    private boolean edit = false;

    public boolean getEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
