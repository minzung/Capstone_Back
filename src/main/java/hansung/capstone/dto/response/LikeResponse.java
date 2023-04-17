package hansung.capstone.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LikeResponse {

    private int countLike;

    private boolean isFilled;

    public boolean getIsFilled() {
        return isFilled;
    }

    public void setIsFilled(boolean filled) {
        isFilled = filled;
    }


}
