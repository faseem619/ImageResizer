package com.backend.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileDetailsService {
    private final FileDetailsRepository fileDetailsRepository;

    @Autowired //dependency injecton
    public FileDetailsService(FileDetailsRepository fileDetailsRepository) {
        this.fileDetailsRepository = fileDetailsRepository;
    }

    public void saveFile(FileDetails fileDetails){
        fileDetailsRepository.save(fileDetails);
    }
    public FileDetails GetFile(String id){
       return fileDetailsRepository.getById(id);
    }
    //find by id returns a type of Optional<ClassName>
    //isPresent necessary to return boolean
    public boolean checkIfExist(String id){
        return fileDetailsRepository.findById(id).isPresent();
    }
    // public void removeRecord(String id){
    //     fileDetailsRepository.deleteById(id);
    // }

    // Function not necessary as hibernate check for duplicate ids on its own
    // and updates records if values change
    //leaving for future reference

    
    // public boolean checkIfExistsWithSameDimensionsAndDeleteIfDimensionsDifferent(FileDetails fileDetails){
    //     if(checkIfExist(fileDetails.getId())){

    //         FileDetails  temp =fileDetailsRepository.getById(fileDetails.getId());
    //         if(temp.getHeight()==fileDetails.getHeight() && temp.getWidth()==fileDetails.getWidth()){
    //             return false;
    //         }
            
    //         removeRecord(fileDetails.getId());
    //         return true;

    //     }
        
    //     return true;
        


    // }

    
}
