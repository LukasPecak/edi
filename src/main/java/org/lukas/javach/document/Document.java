package org.lukas.javach.document;

/**
 * Created by Lukas on 13.08.2019.
 *
 * @author Lukas Pecak
 */
public interface Document {

    DocumentContent getContent();

    void setContent(DocumentContent content);

    DocumentMetadata getMetadata();

    void setMetadata(DocumentMetadata metadata);

}
