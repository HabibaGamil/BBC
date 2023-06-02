package com.example.views.commands;

import com.example.views.ViewService;
import com.example.views.Views;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
@NoArgsConstructor
public class ViewsGetThenDeleteCommand {
    private List<Views> allViews;
    @Autowired
    private ViewService viewService;
    public List<Views> execute()throws IOException {
     allViews = viewService.getAllViews();
     viewService.deleteAllViews();
     return allViews;
    }
}
