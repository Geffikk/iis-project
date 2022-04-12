package org.forum.entities;

import org.forum.entities.user.*;
import org.forum.entities.user.activation.ActivationCode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.*;


public class DemoDatabaseSet {

    public static void main(String[] args) {

        // create session factories
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Post.class)
                .addAnnotatedClass(Topic.class)
                .addAnnotatedClass(Section.class)
                .addAnnotatedClass(ActivationCode.class)
                .addAnnotatedClass(UserAdditionalInfo.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();


        // create sessions

        Session session = factory.getCurrentSession();


        try {

            // create objects
            System.out.println("Creating new objects...");
            User tempUzivatel1 = new User("marosgeffert@gmail.com", "Maros11", "$2y$12$tlG0fqfExC8HNqMv9z5FwOGCseUm1XfCFseAn/MsX2jzuqGFdtQwy", 1, "100,102");
            User tempUzivatel2 = new User("patri.ktomov@gmail.com", "Patres22", "$2y$12$tlG0fqfExC8HNqMv9z5FwOGCseUm1XfCFseAn/MsX2jzuqGFdtQwy", 1, "100,101");
            User tempUzivatel3 = new User("tomaslisicky@zoznam.sk", "TomasLis", "$2y$12$tlG0fqfExC8HNqMv9z5FwOGCseUm1XfCFseAn/MsX2jzuqGFdtQwy", 1, "100,101,103");
            User tempUzivatel4 = new User("adamondrejka@azet.sk", "Adam44", "$2y$12$tlG0fqfExC8HNqMv9z5FwOGCseUm1XfCFseAn/MsX2jzuqGFdtQwy", 1, "100,101");
            User tempUzivatel5 = new User("pavlo123@gmail.com", "Pavlov1c", "$2y$12$tlG0fqfExC8HNqMv9z5FwOGCseUm1XfCFseAn/MsX2jzuqGFdtQwy", 1 , "101,103");

            UserAdditionalInfo tempInfo1 = new UserAdditionalInfo("0904456852", "Maros", "Geffer", "Bardejov", "Student FIT", "VUT FIT");
            UserAdditionalInfo tempInfo2 = new UserAdditionalInfo("0944111254", "Patrik", "Tomov", "Bardejov", "Student FIT", "VUT FIT");
            UserAdditionalInfo tempInfo3 = new UserAdditionalInfo("0922546456", "Tomas", "Lysicky", "Nitra", "Student FIT", "VUT FIT");
            UserAdditionalInfo tempInfo4 = new UserAdditionalInfo("0925657895", "Adam", "Ondejka", "Trencin", "Student FIT", "VUT FIT");
            UserAdditionalInfo tempInfo5 = new UserAdditionalInfo("0944548789", "Andrej", "Pavlovic", "Leopoldov", "Student FIT", "VUT FIT");


            tempUzivatel1.setInfo(tempInfo1);
            tempUzivatel2.setInfo(tempInfo2);
            tempUzivatel3.setInfo(tempInfo3);
            tempUzivatel4.setInfo(tempInfo4);
            tempUzivatel5.setInfo(tempInfo5);

            tempInfo1.setUser(tempUzivatel1);
            tempInfo2.setUser(tempUzivatel2);
            tempInfo3.setUser(tempUzivatel3);
            tempInfo4.setUser(tempUzivatel4);
            tempInfo5.setUser(tempUzivatel5);

            Section tempSkupina1 = new Section("IMA", "Matematika");
            Section tempSkupina2 = new Section("Hry", "Pocitacove hry");
            Section tempSkupina3 = new Section("AutoBazar", "Predaj aut");
            Section tempSkupina4 = new Section("Vtipy", "Rozne vtipy");

            tempSkupina1.setUser(tempUzivatel1);
            tempSkupina2.setUser(tempUzivatel2);
            tempSkupina3.setUser(tempUzivatel1);
            tempSkupina4.setUser(tempUzivatel5);

            tempSkupina1.setModerators_list("Maros11,Patres22,TomasLis");
            tempSkupina2.setModerators_list("Patres22,TomasLis,Adam44");
            tempSkupina3.setModerators_list("Maros12");
            tempSkupina4.setModerators_list("Pavlov1c");


            List<User> moderatorsIMA = new ArrayList<>();
            List<User> moderatorsHry = new ArrayList<>();
            List<User> moderatorsAutoBazar = new ArrayList<>();
            List<User> moderatorsVtipy = new ArrayList<>();


            moderatorsIMA.add(tempUzivatel1);
            moderatorsIMA.add(tempUzivatel2);
            moderatorsIMA.add(tempUzivatel3);
            moderatorsHry.add(tempUzivatel2);
            moderatorsHry.add(tempUzivatel3);
            moderatorsHry.add(tempUzivatel4);
            moderatorsAutoBazar.add(tempUzivatel1);
            moderatorsVtipy.add(tempUzivatel5);

            tempSkupina1.setModerators(moderatorsIMA);
            tempSkupina2.setModerators(moderatorsHry);
            tempSkupina3.setModerators(moderatorsAutoBazar);
            tempSkupina4.setModerators(moderatorsVtipy);

            List<User> membersIMA = new ArrayList<>();
            membersIMA.add(tempUzivatel1);
            membersIMA.add(tempUzivatel2);
            membersIMA.add(tempUzivatel3);
            membersIMA.add(tempUzivatel4);


            List<User> membersHry = new ArrayList<>();
            membersHry.add(tempUzivatel2);
            membersHry.add(tempUzivatel3);
            membersHry.add(tempUzivatel4);
            membersHry.add(tempUzivatel5);

            List<User> membersAutoBazar = new ArrayList<>();
            membersAutoBazar.add(tempUzivatel1);


            List<User> membersVtipy = new ArrayList<>();
            membersVtipy.add(tempUzivatel5);
            membersVtipy.add(tempUzivatel3);


            tempSkupina1.setMembers(membersIMA);
            tempSkupina2.setMembers(membersHry);
            tempSkupina3.setMembers(membersAutoBazar);
            tempSkupina4.setMembers(membersVtipy);

            Topic tempVlakno1 = new Topic("Polsemka", "Čo by podľa vás malo byť na poslemke?");
            Topic tempVlakno2 = new Topic("Skúška", "Budu na skúške aj derivácie?");
            Topic tempVlakno3 = new Topic("Metin 2", "Čo nové vo svete Metinu2?");
            Topic tempVlakno4 = new Topic("CSKO", "Niekto hru?");
            Topic tempVlakno5 = new Topic("Predám ŠKODU 120", "Niekto ponuky?");

            tempVlakno1.setSection(tempSkupina1);
            tempVlakno2.setSection(tempSkupina1);
            tempVlakno3.setSection(tempSkupina2);
            tempVlakno4.setSection(tempSkupina2);
            tempVlakno5.setSection(tempSkupina3);

            tempVlakno1.setUser(tempUzivatel4);
            tempVlakno2.setUser(tempUzivatel3);
            tempVlakno3.setUser(tempUzivatel1);
            tempVlakno4.setUser(tempUzivatel3);
            tempVlakno5.setUser(tempUzivatel1);


            Post tempPrispevok1 = new Post("Podľa mňa logaritmy.", 0);
            Post tempPrispevok2 = new Post("Čo som čítal tak by tam mali byť aj derivácie.", 0);
            Post tempPrispevok3 = new Post("Áno máte pravdu budu tam aj logaritmy aj derivácie.", 0);
            Post tempPrispevok4 = new Post("Nič všetko po starom.", 0);
            Post tempPrispevok5 = new Post("Sme 4 hľadáme ešte 1.", 0);
            Post tempPrispevok6 = new Post("Ja by som šiel keď tak napište mi.", 0);
            Post tempPrispevok7 = new Post("Ak máte ešte voľné pridal by som sa.", 0);


            tempPrispevok1.setTopic(tempVlakno1);
            tempPrispevok2.setTopic(tempVlakno1);
            tempPrispevok3.setTopic(tempVlakno1);
            tempPrispevok4.setTopic(tempVlakno3);
            tempPrispevok5.setTopic(tempVlakno4);
            tempPrispevok6.setTopic(tempVlakno4);
            tempPrispevok7.setTopic(tempVlakno4);


            tempPrispevok1.setUser(tempUzivatel1);
            tempPrispevok2.setUser(tempUzivatel2);
            tempPrispevok3.setUser(tempUzivatel3);
            tempPrispevok4.setUser(tempUzivatel3);
            tempPrispevok5.setUser(tempUzivatel2);
            tempPrispevok6.setUser(tempUzivatel4);
            tempPrispevok7.setUser(tempUzivatel5);

            // start a transactions
            session.beginTransaction();


            // save objectss
            System.out.println("Saving objects...");
            session.save(tempUzivatel1);
            session.save(tempUzivatel2);
            session.save(tempUzivatel3);
            session.save(tempUzivatel4);
            session.save(tempUzivatel5);

            session.save(tempInfo1);
            session.save(tempInfo2);
            session.save(tempInfo3);
            session.save(tempInfo4);
            session.save(tempInfo5);


            session.save(tempSkupina1);
            session.save(tempSkupina2);
            session.save(tempSkupina3);
            session.save(tempSkupina4);

            session.save(tempVlakno1);
            session.save(tempVlakno2);
            session.save(tempVlakno3);
            session.save(tempVlakno4);
            session.save(tempVlakno5);


            session.save(tempPrispevok1);
            session.save(tempPrispevok2);
            session.save(tempPrispevok3);
            session.save(tempPrispevok4);
            session.save(tempPrispevok5);
            session.save(tempPrispevok6);
            session.save(tempPrispevok7);



            // commit transaction
            session.getTransaction().commit();


            System.out.println("DONE!!!");
        }
        finally {
            factory.close();

        }
    }
}
