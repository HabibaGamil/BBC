package DataClasses;

import com.example.views.Views;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ViewsBroadcastRequest {
    private List<Views> views;

}
