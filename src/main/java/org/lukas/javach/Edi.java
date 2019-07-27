package org.lukas.javach;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Lukas on 27.07.2019.
 *
 * @author Lukas Pecak
 */
public class Edi {

    private static Logger LOG = LoggerFactory.getLogger(Edi.class);

    public List<String> loadFile(String fileName) {
        LOG.debug("Loading file for name : {}", fileName);
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            return null;
        }
        LOG.debug("File {} loaded successfully", fileName);

        return lines;
    }
}
