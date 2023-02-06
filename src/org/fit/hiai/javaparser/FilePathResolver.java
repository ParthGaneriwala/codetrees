package org.fit.hiai.javaparser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: April 27, 2019
 * Institution: Florida Institute of Technology
 * Purpose: Find all files of a certain extension within a directory
 */

public class FilePathResolver {
    public FilePathResolver(){}
    public ArrayList<File> resolvePaths(File projectDir, String extension) {
        ArrayList<File> projectFiles = new ArrayList<>();

        new DirExplorer((level, path, file) -> path.endsWith(extension), (level, path, file) ->{
//                String srcFilePath = path.startsWith("/")?path.substring(1, path.length()):path;
            String srcFilePath = projectDir.getPath() + path;
                File foundFile = new File(srcFilePath);
                if(foundFile.isFile())
                    projectFiles.add(foundFile);

        }).explore(projectDir);
        return projectFiles;
    }

    public ArrayList<File> resolvePathsFromList(List<File> fileList, String extension) {
        ArrayList<File> projectFiles = new ArrayList<>();

        for(File projectDir : fileList)
            new DirExplorer((level, path, file) -> path.endsWith(extension), (level, path, file) ->{
                String srcFilePath = projectDir.getPath() + path;
                File foundFile = new File(srcFilePath);
                if(foundFile.isFile())
                    projectFiles.add(foundFile);

            }).explore(projectDir);

        return projectFiles;
    }

    public ArrayList<File> resolvePaths(File projectDir, String extension, List<String> excludedDirs) {
        ArrayList<File> projectFiles = new ArrayList<>();

        new DirExplorer((level, path, file) -> path.endsWith(extension), (level, path, file) ->{
            String srcFilePath = projectDir.getPath() + path;
            for(String excludedDir : excludedDirs) {

                File foundFile = new File(srcFilePath);
                if (foundFile.isFile() && !foundFile.toPath().toString().contains(excludedDir)) // exclude files from certain directories
                    projectFiles.add(foundFile);
            }

        }).explore(projectDir);
        return projectFiles;
    }
}
