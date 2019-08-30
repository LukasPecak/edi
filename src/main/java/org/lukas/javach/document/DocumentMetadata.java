package org.lukas.javach.document;

import org.lukas.javach.exception.UnsatisfiedDocumentMetadataException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

/**
 * Created by Lukas on 15.08.2019.
 *
 * @author Lukas Pecak
 */
public class DocumentMetadata {

    public static DocumentMetadata EMPTY;

    static{
        EMPTY = new DocumentMetadata();
        EMPTY.fileName = "";
        EMPTY.path = Paths.get("");
        EMPTY.creationTime = Instant.MIN;
        EMPTY.lastModifiedTime = Instant.MIN;
        EMPTY.lastAccessTime = Instant.MIN;
    }

    public static class DocumentMetadataBuilder {

        private String fileName;
        private Path path;
        private Instant lastModifiedTime;
        private Instant lastAccessTime;
        private Instant creationTime;
        private long fileSize;
        private boolean regularFile;

        public DocumentMetadataBuilder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public DocumentMetadataBuilder setPath(Path path) {
            this.path = path;
            return this;
        }

        public DocumentMetadataBuilder setLastModifiedTime(Instant lastModifiedTime) {
            this.lastModifiedTime = lastModifiedTime;
            return this;
        }

        public DocumentMetadataBuilder setLastAccessTime(Instant lastAccessTime) {
            this.lastAccessTime = lastAccessTime;
            return this;
        }

        public DocumentMetadataBuilder setCreationTime(Instant creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public DocumentMetadataBuilder setSize(long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public DocumentMetadataBuilder setRegularFile(boolean isRegularFile) {
            this.regularFile = isRegularFile;
            return this;
        }

        public DocumentMetadata build() {
            DocumentMetadata metadata = new DocumentMetadata();

            metadata.fileName = fileName;
            metadata.path = path;
            metadata.lastModifiedTime = lastModifiedTime;
            metadata.lastAccessTime = lastAccessTime;
            metadata.creationTime = creationTime;
            metadata.fileSize = fileSize;
            metadata.regularFile = regularFile;

            ensureAllRequiredDataSet();

            return metadata;
        }

        private void ensureAllRequiredDataSet() {
            if (fileName == null) {
                throw new UnsatisfiedDocumentMetadataException("File name is not set");
            }
            if (path == null) {
                throw new UnsatisfiedDocumentMetadataException("Path is not set");
            }
            if (lastModifiedTime == null) {
                throw new UnsatisfiedDocumentMetadataException("Last modified time is not set");
            }
            if (lastAccessTime == null) {
                throw new UnsatisfiedDocumentMetadataException("Last access time is not set");
            }
            if (creationTime == null) {
                throw new UnsatisfiedDocumentMetadataException("Creation time is not set");
            }
        }

    }

    private String fileName;
    private Path path;
    private Instant lastModifiedTime;
    private Instant lastAccessTime;
    private Instant creationTime;
    private long fileSize;
    private boolean regularFile;

    private DocumentMetadata() {
    }

    String getFileName() {
        return fileName;
    }

    Path getPath() {
        return path;
    }

    public static DocumentMetadataBuilder createBuilder() {
        return new DocumentMetadataBuilder();
    }

    Instant getLastModifiedTime() {
        return lastModifiedTime;
    }

    Instant getLastAccessTime() {
        return lastAccessTime;
    }

    Instant getCreationTime() {
        return creationTime;
    }

    long getSize() {
        return fileSize;
    }

    boolean isRegularFile() {
        return regularFile;
    }

    @Override
    public String toString() {
        return "\tfileName : '" + fileName + '\'' +
                "\n\tpath : " + path +
                "\n\tlastModifiedTime : " + lastModifiedTime +
                "\n\tlastAccessTime : " + lastAccessTime +
                "\n\tcreationTime : " + creationTime +
                "\n\tfileSize : " + fileSize +
                "\n\tregularFile : " + regularFile;
    }
}