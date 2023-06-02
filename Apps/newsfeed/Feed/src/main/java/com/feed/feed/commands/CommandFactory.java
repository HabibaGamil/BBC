package com.feed.feed.commands;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandFactory {

    private final CategoryCommand categoryCommand;
    private final HomePageCommand homePageCommand;
    private final SubCategoryCommand subCategoryCommand;
    private final TopicCommand topicCommand;



    public Command getCommand(String action){
        return switch (action.toLowerCase()) {
            case "categoryaction" -> categoryCommand;
            case "subcategoryaction" -> subCategoryCommand;
            case "topicaction" -> topicCommand;
            case "homepageaction" -> homePageCommand;
            default -> null;
        };

    }
}
