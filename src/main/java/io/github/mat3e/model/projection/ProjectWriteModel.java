package io.github.mat3e.model.projection;

import io.github.mat3e.model.Project;
import io.github.mat3e.model.ProjectStep;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.List;

public class ProjectWriteModel {
    @NotBlank(message = "Project's description must not be empty")
    private String description;
    @Valid
    private List<ProjectStep> steps;

    public ProjectWriteModel() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<ProjectStep> steps() {
        return steps;
    }

    public void setSteps(List<ProjectStep> steps) {
        this.steps = steps;
    }

    public Project toProject(){
        var result = new Project();
        result.setDescription(description);
        steps.forEach(step->step.setProject(result));
        result.setSteps(new HashSet<>(steps));
        return result;
    }
}
