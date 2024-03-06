package io.github.mat3e.logic;

import io.github.mat3e.model.Project;
import io.github.mat3e.model.TaskGroup;
import io.github.mat3e.model.TaskGroupRepository;
import io.github.mat3e.model.TaskRepository;
import io.github.mat3e.model.projection.GroupReadModel;
import io.github.mat3e.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;


//prosrednik pomiedzy repozytorium a controllerem
//@Service
//@RequestScope
public class TaskGroupService {
    private final TaskGroupRepository groupRepository;
    private final TaskRepository taskRepository;

    TaskGroupService(TaskGroupRepository groupRepository, TaskRepository taskRepository) {
        this.groupRepository = groupRepository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(final GroupWriteModel source) {
        return createGroup(source, null);
    }

    GroupReadModel createGroup(final GroupWriteModel source, final Project project) {
        TaskGroup result = groupRepository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return groupRepository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group had undone tasks. Done all tasks first");
        }
        TaskGroup result = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id not found"));

        result.setDone(!result.isDone());
        groupRepository.save(result);
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
