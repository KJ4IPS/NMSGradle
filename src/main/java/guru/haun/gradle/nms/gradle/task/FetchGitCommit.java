package guru.haun.gradle.nms.gradle.task;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.RefSpec;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

/**
 * Created by KJ4IPS on 7/14/2015.
 * Checks out a specific git commit to a specified git directory, using a specified bare copy
 * the branch specified by branch will be fetched prior to pulling the commit
 */
public class FetchGitCommit extends DefaultTask{

    private File basePath;
    private File workTree;
    private String branch;
    private String remote;
    private String commit;

    @Inject
    public FetchGitCommit() {
        super();
    }

    public FetchGitCommit(File basePath, File workTree, String remote, String commit){
        this();
        this.basePath = basePath;
        this.workTree = workTree;
        this.remote = remote;
        this.commit = commit;
    }

    @TaskAction
    public void doFetch() throws IOException, ConfigInvalidException, GitAPIException {

        basePath.mkdirs();
        workTree.mkdirs();

        boolean shouldInit = false;

        FileRepositoryBuilder rb = new FileRepositoryBuilder();
        rb.setGitDir(basePath).readEnvironment().setWorkTree(workTree);

        Repository repo = rb.build();
        if(repo.getObjectDatabase() == null || !repo.getObjectDatabase().exists()) repo.create(true);

        StoredConfig config = repo.getConfig();
        config.setString("remote","spigot","url", remote);
        config.save();

        Git git = new Git(repo);
        git.fetch().setRemote("spigot").setCheckFetchedObjects(true).setRefSpecs(new RefSpec("+refs/heads/".concat(branch))).call();
        git.clean().setCleanDirectories(true).call();
        git.checkout().setStartPoint(commit).setAllPaths(true).call();
        git.close();
        repo.close();
    }

    @Input
    public void setBasePath(File basePath) {
        this.basePath = basePath;
    }

    @Input
    public void setCommit(String commit) {
        this.commit = commit;
    }
    @Input
    public void setRemote(String remote) {
        this.remote = remote;
    }

    @Input
    public void setWorkTree(File workTree) {
        this.workTree = workTree;
    }

    @Input
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public File getBasePath() {
        return basePath;
    }

    public String getCommit() {
        return commit;
    }

    public String getRemote() {
        return remote;
    }

    @OutputDirectory
    public File getWorkTree() {
        return workTree;
    }
}
