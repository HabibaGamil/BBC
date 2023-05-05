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
    byte [] bytes;
    public CustomClassLoader(ClassLoader parent, byte [] bytes){
        super(parent);
        this.bytes=bytes;

    }
    public synchronized Class findClass(String name ) throws ClassNotFoundException
    {
        if ( bytes != null ){
            Class c = defineClass( name, bytes, 0, bytes.length );
            resolveClass(c);
            return c;
        }
        throw new ClassNotFoundException();

    }
   @Override
   public Class loadClass(String name) throws ClassNotFoundException {
      if(name.equals("com.example.test_app.commands.MyCommand")) {
          return this.findClass(name);
      }
      return super.loadClass(name);
   }


}

