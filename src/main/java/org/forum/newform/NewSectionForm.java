package org.forum.newform;

import javax.validation.constraints.Size;

public class NewSectionForm {

    @Size(min = 1, max = 50, message = "{Size.Section.type.validation}")
    private String name;

    @Size(max = 300)
    private String description;

    @Size(max = 10)
    private String isPublic;

    public NewSectionForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }
}
