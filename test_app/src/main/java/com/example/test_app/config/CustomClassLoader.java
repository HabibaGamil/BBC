package com.example.test_app.config;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

public class CustomClassLoader extends ClassLoader{
    String path;
    public CustomClassLoader(String path, ClassLoader parent){
        super(parent);
        this.path=path;
    }

    public synchronized Class findClass( String name ) throws ClassNotFoundException
    {
        byte[] buf = new byte[0];
        try {
            buf = getClassData( path, name );
        } catch (URISyntaxException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        System.out.println("buffer length :  "+buf.length);
        if ( buf != null ){
            System.out.println("name in define class: "+ name);
            return defineClass( name, buf, 0, buf.length );
        }
        throw new ClassNotFoundException();
    }
    protected byte[] getClassData( String directory, String name ) throws URISyntaxException, UnsupportedEncodingException {
        File test = new File(new File(MyCommand.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI()).getPath());
        String path = MyCommand.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        System.out.println("test file size"+ Long.valueOf((new File( path )).length()).intValue());
        System.out.println("path is: "+path);

        String classFile = directory  + name.replace('.','/') + ".class";
       // classFile="MyCommand.class";
        System.out.println("class file "+ classFile);
        int classSize = Long.valueOf((new File( classFile )).length()).intValue();
        System.out.println("class size before "+ classSize);
        byte[] buf = new byte[classSize];
        try {
            FileInputStream filein = new FileInputStream( classFile );
            System.out.println("file in"+ filein);
            classSize = filein.read ( buf );
            System.out.println("class size after"+ classSize);
            filein.close();
        } catch(FileNotFoundException e){
            return null;
        } catch(IOException e){
            return null;
        }
        System.out.println("buffer returned!");
        return buf;
    }

}

