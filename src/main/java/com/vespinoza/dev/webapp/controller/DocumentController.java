/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vespinoza.dev.webapp.controller;

import com.vespinoza.dev.webapp.entity.DocumentEntity;
import com.vespinoza.dev.webapp.service.DocumentService;
import com.vespinoza.dev.webapp.utils.DocumentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author vespinoza
 */
@EnableAutoConfiguration
@RestController
public class DocumentController {
    
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);
    
    @Autowired
    DocumentService documentService;   
    
    @RequestMapping("/")
    public String index() {
        return "Welcome to the upload/download api";    
    }
       
    @RequestMapping(name = "/upload", method = RequestMethod.POST)
    public ResponseEntity handleUpload(HttpServletRequest request){
        logger.info("Trying to upload files");
        try{
            List<FileItem> items = DocumentUtils.getFileItemsFromRequest(request);
            List<DocumentEntity> docIterable = DocumentUtils.getDocumentListFromFileItems(items);
            documentService.saveAll(docIterable);
        } catch(FileUploadException fue){
            logger.info("Error trying to upload files: "+fue.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Upload has been successfull");
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    @RequestMapping(name = "/downloadFile/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        logger.info("Trying to download file with id: "+id);
        DocumentEntity document = documentService.getFileById(id);
        if(document!=null){
            logger.info("Download has been successfull");
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName()+ "\"")
                .body(new ByteArrayResource(document.getByteData()));
        }
        logger.error("Download error, file not found");
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
