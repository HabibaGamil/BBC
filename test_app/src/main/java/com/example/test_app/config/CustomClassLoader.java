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
            System.out.println("!! He is trying to create the new class!! ");
            Class c = defineClass( name, bytes, 0, bytes.length );
            //resolveClass(c);
            return c;
        }
        System.out.println("!! Can't see bytes !!");
        throw new ClassNotFoundException();

    }
   @Override
   public Class loadClass(String name) throws ClassNotFoundException {
      if(name.equals("MyCommand") ) {
          System.out.println("I am in load class !!");
          return this.findClass(name);
      }
       System.out.println(" I reached out of if !!");
      return super.loadClass(name);
   }


}

