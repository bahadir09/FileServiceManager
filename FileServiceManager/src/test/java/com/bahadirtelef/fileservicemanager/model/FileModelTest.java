package com.bahadirtelef.fileservicemanager.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileModelTest {

    @Test
    public void testFileModel() {

        Long id = 1L;
        String fileName = "test.pdf";
        String fileExtension = "pdf";
        String filePath = "/path/to/save/files/test.pdf";
        Long fileSize = 20L;


        FileModel fileModel = new FileModel();
        fileModel.setId(id);
        fileModel.setFileName(fileName);
        fileModel.setFileExtension(fileExtension);
        fileModel.setFilePath(filePath);
        fileModel.setFileSize(fileSize);

        // Doğrulamalar
        Assertions.assertEquals(id, fileModel.getId());
        Assertions.assertEquals(fileName, fileModel.getFileName());
        Assertions.assertEquals(fileExtension, fileModel.getFileExtension());
        Assertions.assertEquals(filePath, fileModel.getFilePath());
        Assertions.assertEquals(fileSize, fileModel.getFileSize());

    }

    public FileModel testNewFileModel() {

        Long id = 1L;
        String fileName = "test.pdf";
        String fileExtension = "pdf";
        String filePath = "/path/to/save/files/test.pdf";
        Long fileSize = 20L;


        FileModel fileModel = new FileModel();
        fileModel.setId(id);
        fileModel.setFileName(fileName);
        fileModel.setFileExtension(fileExtension);
        fileModel.setFilePath(filePath);
        fileModel.setFileSize(fileSize);

        // Doğrulamalar
        Assertions.assertEquals(id, fileModel.getId());
        Assertions.assertEquals(fileName, fileModel.getFileName());
        Assertions.assertEquals(fileExtension, fileModel.getFileExtension());
        Assertions.assertEquals(filePath, fileModel.getFilePath());
        Assertions.assertEquals(fileSize, fileModel.getFileSize());

        return fileModel;
    }
}
