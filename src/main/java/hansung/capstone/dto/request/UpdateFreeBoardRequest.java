package hansung.capstone.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateFreeBoardRequest {

    private int id;

    private String title;

    private String content;

    private boolean isAnonymous;

    public boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

}
