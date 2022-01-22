package com.backend.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileDetailsService {
    private final FileDetailsRepository fileDetailsRepository;

    @Autowired
    public FileDetailsService(FileDetailsRepository fileDetailsRepository) {
        this.fileDetailsRepository = fileDetailsRepository;
    }

    public void saveFile(FileDetails fileDetails){
        fileDetailsRepository.save(fileDetails);
    }

    
}
