package io.github.mat3e.logic;

import io.github.mat3e.model.TaskGroup;
import io.github.mat3e.model.TaskGroupRepository;
import io.github.mat3e.model.TaskRepository;
import io.github.mat3e.model.projection.GroupReadModel;
import io.github.mat3e.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.stream.Collectors;


//prosrednik pomiedzy repozytorium a controllerem
@Service
@RequestScope
public class TaskGroupService {
    private final TaskGroupRepository repository;
    private final TaskRepository taskRepository;

    TaskGroupService(TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group had undone tasks. Done all tasks first");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id not found"));

        result.setDone(!result.isDone());
        repository.save(result);
    }


    //    @Autowired
//    List<String> temp(TaskGroupRepository repository) {
//        //
//        return repository.findAll().stream()
//                .flatMap(taskGroup -> taskGroup.getTasks().stream())
//                .map(Task::getDescription)
//                .collect(Collectors.toList());
    //  }
}
