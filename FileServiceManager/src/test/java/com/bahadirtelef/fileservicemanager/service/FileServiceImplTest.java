package com.bahadirtelef.fileservicemanager.service;

import com.bahadirtelef.fileservicemanager.model.FileModel;
import com.bahadirtelef.fileservicemanager.model.FileModelTest;
import com.bahadirtelef.fileservicemanager.repository.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FileServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileServiceImpl fileService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadFile() throws IOException {
        byte[] fileContent = "Test dosya içeriği".getBytes();
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.pdf", "application/pdf", fileContent);

        FileModelTest fileModelTest = new FileModelTest();
        FileModel fileModel = fileModelTest.testNewFileModel();

        when(fileRepository.save(any(FileModel.class))).thenReturn(fileModel);
        FileModel uploadedFile = fileService.uploadFile(mockFile);

        Assertions.assertNotNull(uploadedFile);
        Assertions.assertEquals(fileModel.getFileName(), uploadedFile.getFileName());
        Assertions.assertEquals(fileModel.getFileExtension(), uploadedFile.getFileExtension());
        Assertions.assertEquals(fileModel.getFileSize(), uploadedFile.getFileSize());
        Assertions.assertEquals(fileModel.getFilePath(), uploadedFile.getFilePath());

        // Dosya kayıt doğrulama
        ArgumentCaptor<FileModel> captor = ArgumentCaptor.forClass(FileModel.class);
        verify(fileRepository, times(1)).save(captor.capture());
        FileModel capturedFileModel = captor.getValue();
        Assertions.assertEquals(fileModel.getFileName(), capturedFileModel.getFileName());
        Assertions.assertEquals(fileModel.getFileExtension(), capturedFileModel.getFileExtension());
        Assertions.assertEquals(fileModel.getFileSize(), capturedFileModel.getFileSize());
        Assertions.assertEquals(fileModel.getFilePath(), capturedFileModel.getFilePath());

    }

    @Test
    public void testUpdateFile() throws IOException {
        byte[] fileContent = "Test dosya içeriği".getBytes();
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.pdf", "application/pdf", fileContent);

        // Mevcut
        FileModel existingFile = new FileModel();
        existingFile.setId(1L);
        existingFile.setFileName("existing.jpg");
        existingFile.setFileExtension("jpg");
        existingFile.setFilePath("/path/to/save/files/existing.jpg");
        existingFile.setFileSize(1000L);

        //Beklenen
        FileModelTest fileModelTest = new FileModelTest();
        FileModel fileModel = fileModelTest.testNewFileModel();

        when(fileRepository.findById(existingFile.getId())).thenReturn(Optional.of(existingFile));
        when(fileRepository.save(any(FileModel.class))).thenReturn(fileModel);
        fileService.updateFile(existingFile.getId(), mockFile);

        ArgumentCaptor<FileModel> captor = ArgumentCaptor.forClass(FileModel.class);
        verify(fileRepository, times(1)).save(captor.capture());
        FileModel capturedFileModel = captor.getValue();
        Assertions.assertEquals(fileModel.getId(), capturedFileModel.getId());
        Assertions.assertEquals(fileModel.getFileName(), capturedFileModel.getFileName());
        Assertions.assertEquals(fileModel.getFileExtension(), capturedFileModel.getFileExtension());
        Assertions.assertEquals(fileModel.getFileSize(), capturedFileModel.getFileSize());
        Assertions.assertEquals(fileModel.getFilePath(), capturedFileModel.getFilePath());
    }

    @Test
    public void testDeleteFile() throws IOException {
        FileModelTest fileModelTest = new FileModelTest();
        FileModel fileModel = fileModelTest.testNewFileModel();

        when(fileRepository.findById(fileModel.getId())).thenReturn(Optional.of(fileModel));
        fileService.deleteFile(fileModel.getId());

        verify(fileRepository, times(1)).findById(fileModel.getId());
        verify(fileRepository, times(1)).delete(fileModel);
        Files.deleteIfExists(Paths.get(fileModel.getFilePath()));

    }

    @Test
    public void testGetAllFiles() {

    }

    @Test
    public void testGetFileById() {

    }

    @Test
    public void testGetFileContent() throws IOException {

    }
}
