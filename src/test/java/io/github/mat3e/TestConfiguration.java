package io.github.mat3e;

import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.*;

@Configuration
public class TestConfiguration {

    @Bean
    @Primary
    @Profile({"!integration"})
    DataSource e2eTestDataSource(){
        var result = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }

    @Bean
    @Primary
    @Profile({"integration"})
    TaskRepository testRepo(){
        return new TaskRepository() {
            final Map<Integer,Task> map = new HashMap<>();

            @Override
            public List<Task> findAll() {
                return new ArrayList<>(map.values());
            }

            @Override
            public Page<Task> findAll(Pageable page) {
                return null;
            }

            @Override
            public Optional<Task> findById(Integer id) {
                return Optional.ofNullable(map.get(id));
            }

            @Override
            public boolean existsById(Integer id) {
                return false;
            }

            @Override
            public boolean existsByDoneIsFalseAndGroup_Id(Integer groupId) {
                return false;
            }

            @Override
            public Task save(Task entity) {
                return map.put(map.size()+1, entity);
            }

            @Override
            public List<Task> findByDone(boolean done) {
                return null;
            }
        };
    }
}
