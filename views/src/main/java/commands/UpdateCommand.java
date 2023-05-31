package commands;


import DataClasses.ViewsResponse;
import com.example.views.ViewService;
import com.example.views.Views;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor
public class UpdateCommand {
    @Autowired
    private ViewService viewService;
    public void execute(String pID)throws IOException {
        viewService.createOrEditViews(pID);
    }

}
