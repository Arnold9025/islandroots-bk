package com.islandroots.bk;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class IslandRootsBkApplicationTests {

    @Test
    void contextLoads() {
        // Teste si l'Application Context Spring Boot charge avec succès : Base DB (H2), Beans et Filters.
    }

}
