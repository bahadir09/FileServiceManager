package com.bahadirtelef.fileservicemanager.service;

import com.bahadirtelef.fileservicemanager.model.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    FileModel uploadFile(MultipartFile file) throws IOException;
    void updateFile(Long fileId, MultipartFile file) throws IOException;
    void deleteFile(Long fileId);
    List<FileModel> getAllFiles();
    FileModel getFileById(Long fileId);
    byte [] getFileContent(Long fileId) throws IOException;
}
