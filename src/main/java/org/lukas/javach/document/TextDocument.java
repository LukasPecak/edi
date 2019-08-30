package org.lukas.javach.document;

/**
 * Created by Lukas on 30.08.2019.
 *
 * @author Lukas Pecak
 */
public class TextDocument implements Document {

    private DocumentContent content;
    private DocumentMetadata metadata;

    TextDocument(DocumentContent content, DocumentMetadata metadata) {
        if (content == null || metadata == null) {
            throw new IllegalArgumentException("Cannot create a document with null content");
        }
        this.content = content;
        this.metadata = metadata;
    }

    @Override
    public DocumentContent getContent() {
        return content;
    }

    @Override
    public void setContent(DocumentContent content) {
        if (content == null) {
            throw new IllegalArgumentException("Cannot set content of null value");
        }
        this.content = content;
    }

    @Override
    public DocumentMetadata getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(DocumentMetadata metadata) {
        if (metadata == null) {
            throw new IllegalArgumentException("Cannot set metadata of null value");
        }
        this.metadata = metadata;
    }
}
