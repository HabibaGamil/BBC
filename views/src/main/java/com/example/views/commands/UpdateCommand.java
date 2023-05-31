package com.example.views.commands;


import com.example.views.ViewService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@NoArgsConstructor
public class UpdateCommand {
    @Autowired
    private ViewService viewService;
    public void execute(String pID)throws IOException {
        viewService.createOrEditViews(pID);
    }

}
