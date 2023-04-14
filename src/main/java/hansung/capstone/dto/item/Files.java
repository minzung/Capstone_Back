package hansung.capstone.dto.item;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Files {

    private String filename;

    @Column(name = "fileOriName")
    private String fileOriName;

    @Column(name = "fileUrl")
    private String fileUrl;

}