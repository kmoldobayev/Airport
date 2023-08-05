package kg.kuban.airport.repository;

import kg.kuban.airport.entity.AppRole;
import kg.kuban.airport.entity.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppUserRepositoryTest {
    @Autowired
    private AppUserRepository appUserRepository;

//    @BeforeAll
//    public static void init() {
//        AppUser user1 = new AppUser();
//        user1.setUserLogin("Test1");
//        user1.setUserPassword("Test1");
//
//        AppUser user2 = new AppUser();
//        user2.setUserLogin("Test2");
//        user2.setUserPassword("Test2");
//
//        //this.appUserRepository.save(user1);
//    }

//    @Test
//    public void testCountUsers_OK() {
//
//        this.appUserRepository.countUsers();
//    }

    @Test
    public void testSave_OK() {
    }
}