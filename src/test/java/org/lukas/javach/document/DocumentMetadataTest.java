package org.lukas.javach.document;

import org.junit.Before;
import org.junit.Test;
import org.lukas.javach.exception.UnsatisfiedDocumentMetadataException;

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

    @Test
    public void getFileName_shouldReturnFileName_whenDocumentWasReadFromFile() {
        // GIVEN
        documentMetadata = prepareBuilderForTests()
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
        documentMetadata = prepareBuilderForTests()
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
        documentMetadata = prepareBuilderForTests()
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
        documentMetadata = prepareBuilderForTests()
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
        documentMetadata = prepareBuilderForTests()
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
        documentMetadata = prepareBuilderForTests()
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
        documentMetadata = prepareBuilderForTests()
                .setRegularFile(isRegularFile)
                .build();

        // WHEN
        boolean isResultFileRegular = documentMetadata.isRegularFile();

        // THEN
        assertThat(isResultFileRegular, is(isRegularFile));
    }

    @Test(expected = UnsatisfiedDocumentMetadataException.class)
    public void build_shouldThrowUnsatisfiedDocumentMetadataException_whenFileNameNotSet() {
        // GIVEN Setup

        // WHEN
        documentMetadata = DocumentMetadata.createBuilder()
                .build();

        // THEN Throw exception
    }

    @Test
    public void build_shouldThrowUnsatisfiedDocumentMetadataExceptionWithMessage_whenFileNameNotSet() {
        // GIVEN
        UnsatisfiedDocumentMetadataException exception = new UnsatisfiedDocumentMetadataException("");
        try{
            documentMetadata = DocumentMetadata.createBuilder()
                    .build();

        } catch(UnsatisfiedDocumentMetadataException e) {
            exception = e;
        }

        // WHEN
        String message = exception.getMessage();

        // THEN
        assertThat(message, is(equalTo("File name is not set")));
    }

    @Test(expected = UnsatisfiedDocumentMetadataException.class)
    public void build_shouldThrowUnsatisfiedDocumentMetadataException_whenPathNotSet() {
        // GIVEN Setup

        // WHEN
        documentMetadata = DocumentMetadata.createBuilder()
                .setFileName("")
                .build();

        // THEN Throw exception
    }

    @Test
    public void build_shouldThrowUnsatisfiedDocumentMetadataExceptionWithMessage_whenPathNotSet() {
        // GIVEN
        UnsatisfiedDocumentMetadataException exception = new UnsatisfiedDocumentMetadataException("");
        try{
            documentMetadata = DocumentMetadata.createBuilder()
                    .setFileName("")
                    .build();

        } catch(UnsatisfiedDocumentMetadataException e) {
            exception = e;
        }

        // WHEN
        String message = exception.getMessage();

        // THEN
        assertThat(message, is(equalTo("Path is not set")));
    }

    @Test(expected = UnsatisfiedDocumentMetadataException.class)
    public void build_shouldThrowUnsatisfiedDocumentMetadataException_whenLastModifiedTimeNotSet() {
        // GIVEN Setup

        // WHEN
        documentMetadata = DocumentMetadata.createBuilder()
                .setFileName("")
                .setPath(Paths.get(""))
                .build();

        // THEN Throw exception
    }

    @Test
    public void build_shouldThrowUnsatisfiedDocumentMetadataExceptionWithMessage_whenLastModifiedTimeNotSet() {
        // GIVEN
        UnsatisfiedDocumentMetadataException exception = new UnsatisfiedDocumentMetadataException("");
        try{
            documentMetadata = DocumentMetadata.createBuilder()
                    .setFileName("")
                    .setPath(Paths.get(""))
                    .build();

        } catch(UnsatisfiedDocumentMetadataException e) {
            exception = e;
        }

        // WHEN
        String message = exception.getMessage();

        // THEN
        assertThat(message, is(equalTo("Last modified time is not set")));
    }

    @Test(expected = UnsatisfiedDocumentMetadataException.class)
    public void build_shouldThrowUnsatisfiedDocumentMetadataException_whenLastAccessTimeNotSet() {
        // GIVEN Setup

        // WHEN
        documentMetadata = DocumentMetadata.createBuilder()
                .setFileName("")
                .setPath(Paths.get(""))
                .setLastModifiedTime(Instant.MIN)
                .build();

        // THEN Throw exception
    }

    @Test
    public void build_shouldThrowUnsatisfiedDocumentMetadataExceptionWithMessage_whenLastAccessTimeNotSet() {
        // GIVEN
        UnsatisfiedDocumentMetadataException exception = new UnsatisfiedDocumentMetadataException("");
        try{
            documentMetadata = DocumentMetadata.createBuilder()
                    .setFileName("")
                    .setPath(Paths.get(""))
                    .setLastModifiedTime(Instant.MIN)
                    .build();

        } catch(UnsatisfiedDocumentMetadataException e) {
            exception = e;
        }

        // WHEN
        String message = exception.getMessage();

        // THEN
        assertThat(message, is(equalTo("Last access time is not set")));
    }

    @Test(expected = UnsatisfiedDocumentMetadataException.class)
    public void build_shouldThrowUnsatisfiedDocumentMetadataException_whenCreationTimeNotSet() {
        // GIVEN Setup

        // WHEN
        documentMetadata = DocumentMetadata.createBuilder()
                .setFileName("")
                .setPath(Paths.get(""))
                .setLastModifiedTime(Instant.MIN)
                .setLastAccessTime(Instant.MIN)
                .build();

        // THEN Throw exception
    }

    @Test
    public void build_shouldThrowUnsatisfiedDocumentMetadataExceptionWithMessage_whenCreationTimeNotSet() {
        // GIVEN
        UnsatisfiedDocumentMetadataException exception = new UnsatisfiedDocumentMetadataException("");
        try{
            documentMetadata = DocumentMetadata.createBuilder()
                    .setFileName("")
                    .setPath(Paths.get(""))
                    .setLastModifiedTime(Instant.MIN)
                    .setLastAccessTime(Instant.MIN)
                    .build();

        } catch(UnsatisfiedDocumentMetadataException e) {
            exception = e;
        }

        // WHEN
        String message = exception.getMessage();

        // THEN
        assertThat(message, is(equalTo("Creation time is not set")));
    }

    private DocumentMetadata.DocumentMetadataBuilder prepareBuilderForTests() {
        return DocumentMetadata.createBuilder()
                .setFileName("")
                .setPath(Paths.get(""))
                .setSize(0L)
                .setCreationTime(Instant.MIN)
                .setLastAccessTime(Instant.MIN)
                .setLastModifiedTime(Instant.MIN)
                .setRegularFile(false);
    }

}
