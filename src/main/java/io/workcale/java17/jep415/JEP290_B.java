package io.workcale.java17.jep415;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JComponent;

public class JEP290_B {

  public static void main(String[] args) throws IOException {

    byte[] bytes = convertObjectToStream(new JComponentExample());
    InputStream is = new ByteArrayInputStream(bytes);
    ObjectInputStream ois = new ObjectInputStream(is);

    // reject all JComponent classes
    ObjectInputFilter jComponentFilter = ObjectInputFilter.rejectFilter(
        JComponent.class::isAssignableFrom,
        ObjectInputFilter.Status.UNDECIDED);
    ois.setObjectInputFilter(jComponentFilter);

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

