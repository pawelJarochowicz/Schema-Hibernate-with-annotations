package annotations.paw.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Application {

    private static SessionFactory factory;

    public static void main(String[] args) {
        factory=HibernateUtil.getSessionFactory();
        addUser("John", "England", 35);
        addUser("Hans", "Germany", 57);
        addUser("Wojtek", "Polska", 29);
        addUser("Sasha", "Russia", 47);
        factory.close();
    }

    public static void addUser(String name, String country, int age)
    {
        Session session= factory.openSession();
        Transaction transaction=null;

        try{
            transaction=session.beginTransaction();
            User user=new User();
            user.setName(name);
            user.setCountry(country);
            user.setAge(age);
            int inserted_id = (Integer) session.save(user);
            System.err.println("Inserted ID : " + inserted_id);
            transaction.commit();
        }catch(Exception e)
        {
            if(transaction!=null)
            {
                transaction.rollback();
            }
            System.out.println("ERROR"+e.getMessage());
        }finally {
            session.close();
        }
    }
}
