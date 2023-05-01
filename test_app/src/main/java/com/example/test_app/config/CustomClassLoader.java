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
        String classFile = directory  + name.replace('.','/') + ".class";

        int classSize = Long.valueOf((new File( classFile )).length()).intValue();
        byte[] buf = new byte[classSize];
        try {
            FileInputStream filein = new FileInputStream( classFile );
            classSize = filein.read ( buf );
            filein.close();
        } catch(FileNotFoundException e){
            return null;
        } catch(IOException e){
            return null;
        }
        return buf;
    }

}

