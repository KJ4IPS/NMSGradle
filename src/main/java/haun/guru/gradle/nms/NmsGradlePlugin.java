package haun.guru.gradle.nms;

import haun.guru.gradle.nms.gradle.task.GetBuildInfoTask;
import haun.guru.gradle.nms.gradle.task.GetVersionJson;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

/**
 * Created by hauna on 7/13/15.
 */
public class NmsGradlePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        Task versionInfoTask = project.getTasks().create("getVersionInfo", GetVersionJson.class);

        Task buildInfoTask = project.getTasks().create("GetBuildInfo", GetBuildInfoTask.class);
        buildInfoTask.dependsOn(versionInfoTask);

    }
}

