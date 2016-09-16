package com.slalom.aws.avs.sutr.conf;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.process.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.xdebugger.DefaultDebugProcessHandler;
import com.slalom.aws.avs.sutr.actions.SutrGenerator;
import com.slalom.aws.avs.sutr.actions.exceptions.SutrGeneratorException;
import com.slalom.aws.avs.sutr.psi.SutrFile;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jordanc on 9/11/2016.
 */
class SutrRunConfiguration extends RunConfigurationBase {
    private String includedSutrFilePattern;

    SutrRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new SutrRunConfigSettingsEditor();
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);
        JDOMExternalizerUtil.addElementWithValueAttribute(element, "SUTR_FILES", getIncludedSutrFilePattern());
    }

    @Override
    public void readExternal(@NotNull Element element) throws InvalidDataException {
        super.readExternal(element);
        setIncludedSutrFilePattern(JDOMExternalizerUtil.getFirstChildValueAttribute(element, "SUTR_FILES"));
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        return new SutrRunBuildState(executionEnvironment);
    }

    String getIncludedSutrFilePattern() {
        return includedSutrFilePattern;
    }

    void setIncludedSutrFilePattern(String includedSutrFilePattern) {
        this.includedSutrFilePattern = includedSutrFilePattern;
    }

    private class SutrRunBuildState extends CommandLineState {
        private SutrRunBuildProcessHandler process;
        private ExecutionEnvironment environment;

        SutrRunBuildState(ExecutionEnvironment environment) {
            super(environment);
            this.environment = environment;
        }

        @NotNull
        @Override
        protected ProcessHandler startProcess() throws ExecutionException {
            process = new SutrRunBuildProcessHandler();
            ProcessTerminatedListener.attach(process);

            ApplicationManager.getApplication().invokeLater(() -> {
                message("Generating Sutr Ask Model...");

                try {
                    startBuild();
                    message("Successfully generated Sutr Ask Model");
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    process.notifyTextAvailable("Failed to generate Sutr Ask Model: " + e, ProcessOutputTypes.STDERR);
                    process.setExitCode(500);
                }

                process.destroyProcess();
            });

            return process;
        }

        private void message(String message) {
            process.notifyTextAvailable(message + "\n", ProcessOutputTypes.STDOUT);
        }

        private void startBuild() throws ExecutionException {
            Project project = environment.getProject();
            ArrayList<VirtualFile> files = new ArrayList<>();

            if (getIncludedSutrFilePattern() == null) {
                return;
            }

            try {
                final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + getIncludedSutrFilePattern());

                Files.walkFileTree(Paths.get(project.getBasePath()), new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                        if (pathMatcher.matches(path)) {
                            message("Including file: " + path.toString());
                            files.add(LocalFileSystem.getInstance().findFileByIoFile(path.toFile()));
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc)
                            throws IOException {
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                throw new ExecutionException(e);
            }

            final PsiManager manager = PsiManager.getInstance(project);
            List<SutrFile> sutrFiles = ContainerUtil.mapNotNull(files, virtualFile -> {
                PsiFile psiFile = manager.findFile(virtualFile);
                return psiFile instanceof SutrFile ? (SutrFile) psiFile : null;
            });

            try {
                SutrGenerator.genererateAsk(sutrFiles);
            } catch (SutrGeneratorException e) {
                e.printStackTrace();
                throw new ExecutionException(e);
            }
        }

        private class SutrRunBuildProcessHandler extends ProcessHandler {
            private Integer myExitCode = 0;

            void setExitCode(Integer exitCode) {
                myExitCode = exitCode;
            }

            @Override
            protected void destroyProcessImpl() {
                this.notifyProcessTerminated(myExitCode);
            }

            @Override
            protected void detachProcessImpl() {
                this.notifyProcessDetached();
            }

            @Override
            public boolean detachIsDefault() {
                return false;
            }

            @Nullable
            @Override
            public OutputStream getProcessInput() {
                return null;
            }
        }
    }
}
