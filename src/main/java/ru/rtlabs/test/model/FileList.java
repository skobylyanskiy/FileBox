package ru.rtlabs.test.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileList {
    private List<String> fileList;

    public FileList() {}

    public FileList(String s) {
        fileList = new ArrayList<>();
        File directory = new File(s);
        if(directory.exists() && directory.isDirectory()) {
            for (File e : directory.listFiles()) {
                if (e.isFile()) {
                    fileList.add(e.getName());
                }
            }
        }
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }
}
