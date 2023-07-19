package com.bahadirtelef.fileservicemanager.controller;

import com.bahadirtelef.fileservicemanager.model.FileModel;
import com.bahadirtelef.fileservicemanager.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<FileModel> uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            FileModel savedFile = fileService.uploadFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public List<FileModel> getAllFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileModel> getFileById(@PathVariable Long id) {
        FileModel file = fileService.getFileById(id);
        if (file != null) {
            return ResponseEntity.ok(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/content")
    public ResponseEntity<byte[]> getFileContentById(@PathVariable Long id) throws IOException {
        byte[] fileContent = fileService.getFileContent(id);
        if (fileContent.length > 0) {
            return ResponseEntity.ok(fileContent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            fileService.updateFile(id, file);
            return ResponseEntity.ok("Dosya başarıyla güncellendi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dosya güncellenirken bir hata oluştu.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok("Dosya başarıyla silindi.");

    }
}
