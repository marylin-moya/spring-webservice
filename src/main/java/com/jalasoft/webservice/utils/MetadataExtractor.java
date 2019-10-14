/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

import static com.jalasoft.webservice.utils.Constants.METADATA_EXT;

public class MetadataExtractor {
    private static final Logger LOGGER = LogManager.getLogger();
    private static String targetFileKey = "file.target-dir";

    /***
     * Generate CSV file with all information related to an image file
     * @param imageFullPath
     * @return
     */
    public static String generateMetadataFile(String imageFullPath) throws ImageProcessingException, IOException {
        String fileNameMetadata = null;
        File file = new File(imageFullPath);
        Metadata metadata =  ImageMetadataReader.readMetadata(file);

        //generate in target-dir directory the metadata file
        fileNameMetadata = String.format("%s%s", PropertiesManager.getInstance().getPropertiesReader().getValue(targetFileKey), file.getName()).concat(METADATA_EXT);
        File metadataFile = new File(fileNameMetadata);
        FileOutputStream pw = new FileOutputStream(metadataFile);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(pw));

        // A Metadata object contains multiple Directory objects
        for (Directory directory : metadata.getDirectories()) {
            // Each Directory stores values in Tag objects
            for (Tag tag : directory.getTags()) {
                bw.write(tag.toString());
                bw.newLine();
            }
        }
        bw.close();
        pw.close();
        return fileNameMetadata ;
    }
}
