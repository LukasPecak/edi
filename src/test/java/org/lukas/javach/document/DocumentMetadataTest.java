package org.lukas.javach.document;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Lukas on 15.08.2019.
 *
 * @author Lukas Pecak
 */
public class DocumentMetadataTest {

    private DocumentMetadata documentMetadata;

    @Before
    public void setupTests() {
//        MockitoAnnotations.initMocks(this);
//        documentMetadata = new DocumentMetadata();
    }

    @Test
    public void getFileName_shouldReturnFileName_whenDocumentWasReadFromFile() {
        // GIVEN
        documentMetadata = DocumentMetadata.createBuilder()
                .setFileName("newFile.txt")
                .build();

        // WHEN
        String fileName = this.documentMetadata.getFileName();

        // THEN
        assertThat(fileName, is(equalTo("newFile.txt")));
    }

    @Test
    public void getPath_shouldReturnTheCompletePath_whenDocumentWasReadFromFile() {
        // GIVEN
        Path originPath = Paths.get("src/test/resources/newFile.txt");
        documentMetadata = DocumentMetadata.createBuilder()
                .setPath(originPath)
                .build();

        // WHEN
        Path path = this.documentMetadata.getPath();

        // THEN
        assertThat(path, is(equalTo(originPath)));
    }

    @Test
    public void getLastModifiedTime_shouldReturnTheTimeOfLastModification_whenSet() {
        // GIVEN
        Instant testTimestamp = Instant.ofEpochMilli(1_000L);
        documentMetadata = DocumentMetadata.createBuilder()
                .setLastModifiedTime(testTimestamp)
                .build();

        // WHEN
        Instant result = documentMetadata.getLastModifiedTime();

        // THEN
        assertThat(result, is(equalTo(Instant.ofEpochMilli(1_000L))));
    }

    @Test
    public void getLastAccessTime_shouldReturnTheTimeOfLastAccess_whenSet() {
        // GIVEN
        Instant testTimestamp = Instant.ofEpochMilli(1_000L);
        documentMetadata = DocumentMetadata.createBuilder()
                .setLastAccessTime(testTimestamp)
                .build();

        // WHEN
        Instant result = documentMetadata.getLastAccessTime();

        // THEN
        assertThat(result, is(equalTo(Instant.ofEpochMilli(1_000L))));
    }

    @Test
    public void getCreationTime_shouldReturnTheTimeOfCreation_whenSet() {
        // GIVEN
        Instant testTimestamp = Instant.ofEpochMilli(1_000L);
        documentMetadata = DocumentMetadata.createBuilder()
                .setCreationTime(testTimestamp)
                .build();

        // WHEN
        Instant result = documentMetadata.getCreationTime();

        // THEN
        assertThat(result, is(equalTo(Instant.ofEpochMilli(1_000L))));
    }

    @Test
    public void getSize_shouldReturnTheSizeOfFile_whenSet() {
        // GIVEN
        long testFileSize = 1_000L;
        documentMetadata = DocumentMetadata.createBuilder()
                .setSize(testFileSize)
                .build();

        // WHEN
        long size = documentMetadata.getSize();

        // THEN
        assertThat(size, is(equalTo(1_000L)));
    }

    @Test
    public void isRegularFile_shouldReturnTrue_whenFileIsRegularOrNewCreated() {
        // GIVEN
        boolean isRegularFile = true;
        documentMetadata = DocumentMetadata.createBuilder()
                .setRegularFile(isRegularFile)
                .build();

        // WHEN
        boolean isResultFileRegular = documentMetadata.isRegularFile();

        // THEN
        assertThat(isResultFileRegular, is(isRegularFile));
    }
}
