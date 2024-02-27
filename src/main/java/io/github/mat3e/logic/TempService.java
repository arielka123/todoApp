package io.github.mat3e.logic;

import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class TempService {
    @Autowired
    List<String> temp(TaskGroupRepository repository) {
        //
        return repository.findAll().stream()
                .flatMap(taskGroup -> taskGroup.getTasks().stream())
                .map(Task::getDescription)
                .collect(Collectors.toList());
    }
}
