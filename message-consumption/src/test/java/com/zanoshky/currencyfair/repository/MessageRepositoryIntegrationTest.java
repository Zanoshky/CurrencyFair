package com.zanoshky.currencyfair.repository;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.zanoshky.currencyfair.common.dto.VolumeMessage;
import com.zanoshky.currencyfair.model.Message;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class MessageRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void whenFindAllSinceLastId_thenReturnListOfVolumeMessages() {
        // given:
        final String currencyFrom = "HRK";
        final String currencyTo = "EUR";

        final Message first = new Message("1234567", currencyFrom, currencyTo, 1000.0, 750.0, 0.75, new Date(), "HR");

        entityManager.persist(first);
        entityManager.flush();

        // when:
        final List<VolumeMessage> found = messageRepository.findAllOnlyVolumeInfo(0L);

        // then:
        assert found.size() == 1;
        assert found.get(0).getCurrencyFrom().equals(currencyFrom);
        assert found.get(0).getCurrencyTo().equals(currencyTo);
    }

}