package io.github.mat3e.reports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface PersistedTaskEventRepository extends JpaRepository<PersistedTaskEvent, Integer> {
    List<PersistedTaskEvent> findByTaskId(int taskId);
    List<PersistedTaskEvent> findByTaskIdAndNameOrderByOccurrenceDesc(int taskId, String name);
}
