package com.example.springbootelasticsearch.DataClasses;

import com.example.springbootelasticsearch.entity.Views;
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
