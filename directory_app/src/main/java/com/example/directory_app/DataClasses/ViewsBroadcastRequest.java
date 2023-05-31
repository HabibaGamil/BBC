package com.example.directory_app.DataClasses;

import com.example.directory_app.entities.Views;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ViewsBroadcastRequest {
    private List<Views> views;

}
