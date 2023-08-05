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
class AppRoleRepositoryTest {

    @Autowired
    private AppRoleRepository appRoleRepository;

//    @BeforeAll
//    public static void init() {
//        AppUser user1 = new AppUser();
//        user1.setUserLogin("Test1");
//        user1.setUserPassword("Test1");
//
//    }

    @Test
    public void testSave_OK() {
        AppRole appRole = new AppRole();
        appRole.setTitle("TEST");
        this.appRoleRepository.save(appRole);
        AppRole saveRole = this.appRoleRepository.findByTitle("TEST");
        Assertions.assertEquals(appRole.getTitle(), saveRole.getTitle());
    }
}