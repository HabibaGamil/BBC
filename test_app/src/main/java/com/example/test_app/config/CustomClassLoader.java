package com.example.test_app.config;

import jdk.swing.interop.SwingInterOpUtils;
import lombok.Synchronized;
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
        System.out.println("My custom loader");
        this.path=path;
    }

    public synchronized Class findClass(String name ) throws ClassNotFoundException
    {
        System.out.println("was in find Class");
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
            Class c = defineClass( name, buf, 0, buf.length );
            System.out.println("finished Define Class");
            resolveClass(c);
            return c;
        }
        throw new ClassNotFoundException();

    }
   @Override
   public Class loadClass(String name) throws ClassNotFoundException {
      if(name.equals("MyCommand"))
         return this.findClass(name);
      return super.loadClass(name);

   }
    protected byte[] getClassData( String directory, String name ) throws URISyntaxException, UnsupportedEncodingException {
        System.out.println("in getClassData");
        String classFile = directory  + name.replace('.','/') + ".class";

        int classSize = Long.valueOf((new File( classFile )).length()).intValue();
        System.out.println("classFile" +classFile);
        System.out.println("class size is :"+ classSize);
        System.out.println("string name input "+ name);
        byte[] buf = new byte[classSize];
        try {
            FileInputStream filein = new FileInputStream( classFile );
            classSize = filein.read ( buf );
            filein.close();
        } catch(FileNotFoundException e){
            System.out.println("file not found exception");
            return null;
        } catch(IOException e){
            System.out.println("IO exception");
            return null;
        }
        return buf;
    }

}

