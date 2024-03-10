package io.github.mat3e.logic;

import io.github.mat3e.model.TaskGroup;
import io.github.mat3e.model.repository.TaskGroupRepository;
import io.github.mat3e.model.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("should throw exception when group has undone tasks")
    void toggleGroup_unDoneTasks_inGroup_throwsIllegalStateException() {
        //given
        var taskRepository = taskRepositoryReturning(true);

        //when
        var toTest = new TaskGroupService(null,taskRepository);
        var exception = catchThrowable(()->toTest.toggleGroup(1));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone tasks");
        }

    @Test
    @DisplayName("should throw exception when tasks group is not found")
    void toggleGroup_givenGroupId_notFound_throwsIllegalArgumentException() {
        //given
        var taskRepository = taskRepositoryReturning(false);

        var groupRepository = mock(TaskGroupRepository.class);
        when(groupRepository.findById(anyInt())).thenReturn(Optional.empty());

        //when
        var toTest = new TaskGroupService(groupRepository, taskRepository);
        var exception = catchThrowable(() -> toTest.toggleGroup(1));

        //then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("should toggle group")
    void toggleGroup() {
        //given
        var taskRepository = taskRepositoryReturning(false);

        var taskGroup = new TaskGroup();
        var beforeToggle = taskGroup.isDone();

        var groupRepository = mock(TaskGroupRepository.class);
        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(taskGroup));

        //when
        var toTest = new TaskGroupService(groupRepository, taskRepository);
        toTest.toggleGroup(1);

        //then
        assertThat(taskGroup.isDone()).isEqualTo(!beforeToggle);
    }

    private static TaskRepository taskRepositoryReturning(boolean value) {
        var taskRepository = mock(TaskRepository.class);
        when(taskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(value);
        return taskRepository;
    }
}