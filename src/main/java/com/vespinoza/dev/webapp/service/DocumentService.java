/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vespinoza.dev.webapp.service;

import com.vespinoza.dev.webapp.entity.DocumentEntity;
import com.vespinoza.dev.webapp.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vespinoza
 */
@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;
    
    public void save(DocumentEntity document){
        documentRepository.save(document);
    }
    
    public void saveAll(Iterable<DocumentEntity> documentIterable){
        documentRepository.saveAll(documentIterable);
    }
    
    public DocumentEntity getFileById(Long id){
        return documentRepository.findById(id).orElse(null);
    }
}
