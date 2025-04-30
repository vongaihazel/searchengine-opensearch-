package com.example.searchengine.repository;

import com.example.searchengine.model.SearchHistory;
import com.example.searchengine.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SearchHistoryRepositoryTest {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testuser");
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        searchHistoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testInsertSearchHistory() {
        SearchHistory history = new SearchHistory();
        history.setUser(user);
        history.setQuery("example query");
        history.setTimestamp(LocalDateTime.now());

        searchHistoryRepository.save(history);

        List<SearchHistory> histories = searchHistoryRepository.findByUserId(user.getId());
        assertThat(histories).hasSize(1);
        assertThat(histories.get(0).getQuery()).isEqualTo("example query");
    }
}
