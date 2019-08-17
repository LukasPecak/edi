package org.lukas.javach.document;

import java.nio.file.Path;
import java.time.Instant;

/**
 * Created by Lukas on 15.08.2019.
 *
 * @author Lukas Pecak
 */
class DocumentMetadata {

    static class DocumentMetadataBuilder {

        private String fileName;
        private Path path;
        private Instant lastModifiedTime;
        private Instant lastAccessTime;
        private Instant creationTime;
        private long fileSize;
        private boolean regularFile;

        DocumentMetadataBuilder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        DocumentMetadataBuilder setPath(Path path) {
            this.path = path;
            return this;
        }

        DocumentMetadataBuilder setLastModifiedTime(Instant lastModifiedTime) {
            this.lastModifiedTime = lastModifiedTime;
            return this;
        }

        DocumentMetadataBuilder setLastAccessTime(Instant lastAccessTime) {
            this.lastAccessTime = lastAccessTime;
            return this;
        }

        DocumentMetadataBuilder setCreationTime(Instant creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        DocumentMetadataBuilder setSize(long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        DocumentMetadataBuilder setRegularFile(boolean isRegularFile) {
            this.regularFile = isRegularFile;
            return this;
        }

        DocumentMetadata build() {
            DocumentMetadata metadata = new DocumentMetadata();

            metadata.fileName = fileName;
            metadata.path = path;
            metadata.lastModifiedTime = lastModifiedTime;
            metadata.lastAccessTime = lastAccessTime;
            metadata.creationTime = creationTime;
            metadata.fileSize = fileSize;
            metadata.regularFile = regularFile;

            return metadata;
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

    static DocumentMetadataBuilder createBuilder() {
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
}