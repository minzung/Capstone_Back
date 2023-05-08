package hansung.capstone.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateFreeBoardRequest {

    private int id;

    private String title;

    private String content;

    private boolean anonymous;

    private String imageFile;

    public boolean getIsAnonymous() {
        return anonymous;
    }

}
