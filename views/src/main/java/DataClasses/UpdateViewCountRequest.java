package DataClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateViewCountRequest {
    // whenever a get request is made to a post
    private String postMetadataId;

}