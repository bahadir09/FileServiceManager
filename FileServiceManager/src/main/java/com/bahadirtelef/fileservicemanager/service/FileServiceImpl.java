package com.bahadirtelef.fileservicemanager.service;

import com.bahadirtelef.fileservicemanager.model.FileModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public FileModel uploadFile(MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public void updateFile(Long fileId, MultipartFile file) throws IOException {

    }

    @Override
    public void deleteFile(Long fileId) {

    }

    @Override
    public List<FileModel> getAllFiles() {
        return null;
    }

    @Override
    public FileModel getFileById(Long fileId) {
        return null;
    }

    @Override
    public byte[] getFileContent(Long fileId) throws IOException {
        return new byte[0];
    }
}
