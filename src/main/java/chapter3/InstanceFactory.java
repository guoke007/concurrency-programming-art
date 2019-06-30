package chapter3;

/**
 * Created by Administrator on 2019/5/3.
 */
public class InstanceFactory {
     private static   Person  person;
     public  static  Person getInstance() {
        if (person == null)
            synchronized (InstanceFactory.class) {
                if (person == null)
                    person = new Person();
            }
        return person;
    }

    /*  public synchronized static Person getInstance() {
          if (person == null)
              person = new Person();
          return person;
      }*/
   /* private static class InstanceHolder {
        public static Person person = new Person();
    }

    public static Person getInstance() {
        return InstanceHolder.person;
    }*/
   /* private static Person person = new Person();

    public static Person getInstance() {
        return person;
    }
*/
    public static void main(String[] args) {
        Person instance1 = InstanceFactory.getInstance();
        Person instance2 = InstanceFactory.getInstance();
        System.out.println(instance1==instance2);
    }
}
