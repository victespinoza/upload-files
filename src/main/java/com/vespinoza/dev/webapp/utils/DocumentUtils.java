/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vespinoza.dev.webapp.utils;

import com.vespinoza.dev.webapp.entity.DocumentEntity;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author vespinoza
 */
public class DocumentUtils {
    
    public static List<FileItem> getFileItemsFromRequest(HttpServletRequest request) throws FileUploadException{
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(
          new File(System.getProperty("java.io.tmpdir")));
        factory.setSizeThreshold(
          DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
        factory.setFileCleaningTracker(null);

        ServletFileUpload upload = new ServletFileUpload(factory);

        return upload.parseRequest(request);
    }

    public static List<DocumentEntity> getDocumentListFromFileItems(List<FileItem> items) {
        Iterator<FileItem> iter = items.iterator();
        List<DocumentEntity> docEntityList = new ArrayList<>();
        while (iter.hasNext()) {
            docEntityList.add(new DocumentEntity(iter.next()));
        }
        return docEntityList;
    }
}
