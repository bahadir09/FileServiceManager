package com.bahadirtelef.fileservicemanager.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Data
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileExtension;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private Long fileSize;

}
