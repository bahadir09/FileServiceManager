package com.bahadirtelef.fileservicemanager.service;

import com.bahadirtelef.fileservicemanager.model.FileModel;
import com.bahadirtelef.fileservicemanager.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileServiceImpl implements FileService{

    private final FileRepository fileRepository;

    private static final int MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    @Override
    public FileModel uploadFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = getFileExtension(fileName);
        long fileSize = file.getSize();

        // Check file size and extension
        if (fileSize > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Dosya boyutu 5MB'tan büyük olamaz.");
        }

        if (!isSupportedExtension(extension)) {
            throw new IllegalArgumentException("Desteklenmeyen dosya uzantısı: " + extension);
        }
        String filePath = saveFile(file);

        FileModel fileModel = new FileModel();
        fileModel.setFileName(fileName);
        fileModel.setFileExtension(extension);
        fileModel.setFilePath(filePath);
        fileModel.setFileSize(file.getSize());
        return fileRepository.save(fileModel);
    }

    @Override
    public void updateFile(Long fileId, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(fileName);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Dosya boş olamaz.");
        }
        if (!isSupportedExtension(fileExtension)) {
            throw new IllegalArgumentException("Desteklenmeyen dosya uzantısı.");
        }

        Optional<FileModel> optionalFile = fileRepository.findById(fileId);
        if (optionalFile.isPresent()) {
            FileModel existingFile = optionalFile.get();

            String filePath = saveFile(file);

            existingFile.setFileName(fileName);
            existingFile.setFileExtension(fileExtension);
            existingFile.setFilePath(filePath);
            existingFile.setFileSize(file.getSize());
            fileRepository.save(existingFile);
        } else {
            throw new IllegalArgumentException("Dosya Bulunamadı.");
        }
    }

    @Override
    public void deleteFile(Long fileId) {
        Optional<FileModel> optionalFile = fileRepository.findById(fileId);
        if (optionalFile.isPresent()) {
            FileModel file = optionalFile.get();
            try {
                Files.deleteIfExists(Paths.get(file.getFilePath()));
                fileRepository.delete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Dosya Bulunamadı.");
        }
    }

    @Override
    public List<FileModel> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public FileModel getFileById(Long fileId) {
        Optional<FileModel> optionalFile = fileRepository.findById(fileId);
        return optionalFile.orElse(new FileModel());
    }

    @Override
    public byte[] getFileContent(Long fileId) throws IOException {
        Optional<FileModel> optionalFile = fileRepository.findById(fileId);
        FileModel file = optionalFile.get();
        byte[] fileContent = Files.readAllBytes(Paths.get(file.getFilePath()));
        return fileContent;
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private boolean isSupportedExtension(String fileExtension) {
        List<String> supportedExtensions = Arrays.asList("png", "jpeg", "jpg", "docx", "pdf", "xlsx");
        return supportedExtensions.contains(fileExtension.toLowerCase());
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String folderPath = "/path/to/save/files"; // Dosyaların kaydedileceği klasör yolu
        //String folderPath = "C:/Users/bahad/OneDrive/Masaüstü/dene";
        String filePath = folderPath + "/" + fileName;

        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            return filePath;
        }
    }
}
