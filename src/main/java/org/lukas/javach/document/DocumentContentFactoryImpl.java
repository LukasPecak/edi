package org.lukas.javach.document;

/**
 * Created by Lukas on 17.08.2019.
 *
 * @author Lukas Pecak
 */
public class DocumentContentFactoryImpl implements DocumentContentFactory {

    @Override
    public DocumentContent createDocumentContent(byte[] bytes) {
        return new TextContent(bytes);
    }
}
