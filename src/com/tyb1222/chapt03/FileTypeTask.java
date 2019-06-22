package com.tyb1222.chapt03;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FileTypeTask extends RecursiveAction {

    private File path;

    public FileTypeTask(File path) {
        this.path = path;
    }

    @Override
    protected void compute() {
        List<FileTypeTask> fileTypes = new ArrayList<>();
        File [] files = path.listFiles();
        if (null!= files){
            for (File file : files) {
                if (file.isDirectory()){
                    fileTypes.add(new FileTypeTask(file));
                }
                else{
                    if (file.getAbsolutePath().endsWith(".java")){
                        System.out.println("file :" +file.getAbsolutePath());
                    }
                }
            }
            if (!fileTypes.isEmpty()){
                final Collection<FileTypeTask> typeCollection = invokeAll(fileTypes);
                for(FileTypeTask task: typeCollection){
                    task.join();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        FileTypeTask fileType = new FileTypeTask(new File("/home/tyb/IdeaProjects/parallel/src/com/tyb1222"));
        long begin = System.currentTimeMillis();
        pool.execute(fileType);
        Thread.sleep(1000);
        for (int i =0 ;i <10;i++){
            System.out.println("main runs.");
            Thread.sleep(10);
        }
        fileType.join();
        long end = System.currentTimeMillis();
        System.out.println("cost time:" + (end -begin));
    }
}
