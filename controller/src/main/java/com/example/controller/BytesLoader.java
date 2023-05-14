package com.example.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BytesLoader {

    String path ;
    public BytesLoader(){
        //initializing the path through which any new/updates class file will be added (this is the path to the commands folder)
        Path currRelativePath = Paths.get("");
        path = currRelativePath.toAbsolutePath().toString()+ "\\target\\classes\\";
        path+="com\\example\\controller\\commands\\";
        System.out.println(path);
    }

    //This method loads the byte code from the file using the specified path
    public byte [] getBytesArray(String name) throws IOException {

        String classFile = path  + name.replace('.','/') + ".class";
        int classSize = Long.valueOf((new File( classFile )).length()).intValue();
        byte[] buf = new byte[classSize];
        FileInputStream fileInput = new FileInputStream( classFile );
        classSize = fileInput.read ( buf );
        fileInput.close();
        return buf;

    }

}
