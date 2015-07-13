package haun.guru.gradle.nms.gradle;

import org.gradle.api.Task;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.SelfResolvingDependency;
import org.gradle.api.tasks.TaskDependency;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hauna on 7/13/15.
 */
public class NmsDependancy implements SelfResolvingDependency {

    private String version;

    @Override
    public Set<File> resolve() {
        return null;
    }

    @Override
    public Set<File> resolve(boolean b) {
        return resolve(); //NYI
    }

    @Override
    public TaskDependency getBuildDependencies() {

        TaskDependency deps = new TaskDependency() {
            @Override
            public Set<? extends Task> getDependencies(Task task) {
                return new HashSet<Task>() {
                    //add tasks here
                };
            }
        };
        throw new RuntimeException("Not Yet Implemented");
    }

    @Override
    public String getGroup() {
        return "net.minecraft";
    }

    @Override
    public String getName() {
        return "minecraft_server";
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public boolean contentEquals(Dependency dependency) {
        return false;
    }

    @Override
    public Dependency copy() {
        return null;
    }
}
