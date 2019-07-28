package org.lukas.javach;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Lukas on 27.07.2019.
 *
 * @author Lukas Pecak
 */
class Edi {

    private static final Logger LOG = LoggerFactory.getLogger(Edi.class);

    byte[] loadBytes(String pathString) {
        LOG.debug("Loading file for name : {}", pathString);
        if (pathString == null || pathString.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(pathString));
        } catch (IOException e) {
            LOG.error("Error while trying to read file");
            return null;
        }
        LOG.debug("File {} loaded successfully", pathString);

        return bytes;
    }

    void saveBytes(byte[] bytes, String pathString) {
        try {
            Files.write(Paths.get(pathString), bytes);
        } catch (IOException e) {
            LOG.error("Error while trying to save file");
        }
    }
}
