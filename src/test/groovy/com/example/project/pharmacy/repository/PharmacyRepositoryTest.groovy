package com.example.project.pharmacy.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class PharmacyRepositoryTest extends Specification {

    @Autowired
    private PharmacyRepository pharmacyRepository

    def "test"() {

    }
}
