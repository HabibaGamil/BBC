package DataClasses;
import com.example.views.Views;
import lombok.*;
import java.util.List;
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor

public class ViewsResponse {
    private List<Views> views;

}
