package io.workcale.java17.jep415;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JEP290_A {


  public static void main(String[] args) throws IOException {

    byte[] bytes = convertObjectToStream(new DdosExample());
    InputStream is = new ByteArrayInputStream(bytes);
    ObjectInputStream ois = new ObjectInputStream(is);

    // Setting a Custom Filter Using a Pattern
    // need full package path
    // the maximum number of bytes in the input stream = 1024
    // allows classes in com.mkyong.java17.jep415.*
    // allows classes in the java.base module
    // rejects all other classes !*
    ObjectInputFilter filter1 =
        ObjectInputFilter.Config.createFilter(
            "maxbytes=1024;io.workcale.java17.*;java.base/*;!*");
    ois.setObjectInputFilter(filter1);

    try {
      Object obj = ois.readObject();
      System.out.println("Read obj: " + obj);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static byte[] convertObjectToStream(Object obj) {
    ByteArrayOutputStream boas = new ByteArrayOutputStream();
    try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
      ois.writeObject(obj);
      return boas.toByteArray();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    throw new RuntimeException();
  }

}

