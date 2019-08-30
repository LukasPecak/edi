package org.lukas.javach.document;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Lukas on 30.08.2019.
 *
 * @author Lukas Pecak
 */
public class TextDocumentTest {

    private TextDocument sut;

    @Mock
    private DocumentContent content;
    @Mock
    private DocumentMetadata metadata;

    @Before
    public void setupTest() {
        MockitoAnnotations.initMocks(this);
        sut = new TextDocument(content, metadata);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldThrowIllegalArgumentException_whenTryToCreateNewDocumentWithContentNullValue() {
        // GIVEN
        DocumentContent content = null;

        // WHEN
        new TextDocument(content, metadata);

        // THEN should throw exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldThrowIllegalArgumentException_whenTryToCreateNewDocumentWithMetadataOfNullValue() {
        // GIVEN
        DocumentMetadata metadata = null;

        // WHEN
        new TextDocument(content, metadata);

        // THEN should throw exception
    }

    @Test
    public void constructor_shouldCreateDocument_whenNoNullArgumentSuppliedToConstructor() {
        // GIVEN setup

        // WHEN
        sut = new TextDocument(content, metadata);

        // THEN should throw exception
        assertThat(sut, is(notNullValue()));
    }


    @Test
    public void getContent_shouldReturnNonNullValue_always() {
        // GIVEN setup

        // WHEN
        DocumentContent content = sut.getContent();

        // THEN
        assertThat(content, is(notNullValue()));
    }

    @Test
    public void getMetadata_shouldReturnNonNullValue_always() {
        // GIVEN setup

        // WHEN
        DocumentMetadata metadata = sut.getMetadata();

        // THEN
        assertThat(metadata, is(notNullValue()));
    }

    @Test
    public void setMetadata_shouldSetTheDocumentMetadata_whenNotNullValue() {
        // GIVEN
        String fileName = "file.txt";
        Path path = Paths.get("c:/documents");
        Instant lastAccessAndModifiedTime = Instant.ofEpochSecond(1_000_000L);
        Instant creationTime = Instant.ofEpochSecond(500_000L);
        DocumentMetadata givenMetadata = DocumentMetadata.createBuilder()
                .setFileName(fileName)
                .setPath(path)
                .setLastAccessTime(lastAccessAndModifiedTime)
                .setLastModifiedTime(lastAccessAndModifiedTime)
                .setCreationTime(creationTime)
                .build();

        // WHEN
        sut.setMetadata(givenMetadata);
        DocumentMetadata metadata = sut.getMetadata();

        // THEN
        assertThat(metadata, is(notNullValue()));
        assertThat(metadata.getFileName(), is(equalTo(fileName)));
        assertThat(metadata.getPath(), is(equalTo(path)));
        assertThat(metadata.getLastAccessTime(), is(equalTo(lastAccessAndModifiedTime)));
        assertThat(metadata.getLastModifiedTime(), is(equalTo(lastAccessAndModifiedTime)));
        assertThat(metadata.getCreationTime(), is(equalTo(creationTime)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMetadata_shouldThrowIllegalArgumentException_whenTryingToSetNullValue() {
        // GIVEN
        DocumentMetadata givenMetadata = null;

        // WHEN
        sut.setMetadata(givenMetadata);

        // THEN throw exception
    }

@Test
    public void setContent_shouldSetTheDocumentContent_whenNotNullValue() {
        // GIVEN
        String contentText = "This is a text content";
        DocumentContent givenContent = new TextContent(contentText.getBytes());

        // WHEN
        sut.setContent(givenContent);
        DocumentContent content = sut.getContent();

        // THEN
        assertThat(content, is(notNullValue()));
        assertThat(new String(content.getBytes()), is(equalTo(contentText)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setContent_shouldThrowIllegalArgumentException_whenTryingToSetNullValue() {
        // GIVEN
        DocumentContent givenContent = null;

        // WHEN
        sut.setContent(givenContent);

        // THEN throw exception
    }




}