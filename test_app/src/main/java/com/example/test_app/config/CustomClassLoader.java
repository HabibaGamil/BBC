package com.example.test_app.config;

public class CustomClassLoader extends ClassLoader{
    byte [] bytes;
    public CustomClassLoader(ClassLoader parent, byte [] bytes){
        super(parent);
        this.bytes=bytes;

    }
    public synchronized Class findClass(String name) throws ClassNotFoundException
    {
        if ( bytes != null ){
            Class c = defineClass( null , bytes, 0, bytes.length );
            return c;
        }
        throw new ClassNotFoundException();

    }
   @Override
   public Class loadClass(String name) throws ClassNotFoundException {
      if(name.equals("MyCommand") ) {
          return this.findClass(name);
      }
      return super.loadClass(name);
   }


}

