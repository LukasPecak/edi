package org.lukas.javach;

import org.lukas.javach.document.*;
import org.lukas.javach.editor.Editor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Lukas on 27.07.2019.
 *
 * @author Lukas Pecak
 */
class Edi {

    private static final Logger LOG = LoggerFactory.getLogger(Edi.class);

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            LOG.error("Too many arguments. Expected : 1 but was : {}. " +
                    "Please specify only the path to the edited file ...", args.length);
        }
        DocumentContentFactory contentFactory = new DocumentContentFactoryImpl();
        Document document;
        if (args.length == 1) {
            DocumentContent content;
            DocumentMetadata metadata;
            byte[] loadedBytes = new Edi().loadBytes(args[0]);
            if (loadedBytes == null) {
                content = contentFactory.createDocumentContent(new byte[0]);
                metadata = DocumentMetadata.EMPTY;
            } else {
                content = contentFactory.createDocumentContent(loadedBytes);
                Path path = Paths.get(args[0]);
                BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
                metadata = DocumentMetadata.createBuilder()
                        .setFileName(path.getFileName().toString())
                        .setPath(path)
                        .setCreationTime(attributes.creationTime().toInstant())
                        .setLastModifiedTime(attributes.lastModifiedTime().toInstant())
                        .setLastAccessTime(attributes.lastAccessTime().toInstant())
                        .setRegularFile(attributes.isRegularFile())
                        .setSize(attributes.size())
                        .build();
            }
            document = new TextDocument(content, metadata);
        } else {
            DocumentContent content = contentFactory.createDocumentContent(new byte[0]);
            DocumentMetadata metadata = DocumentMetadata.EMPTY;

            document = new TextDocument(content, metadata);
        }

        Editor editor = new Editor();
        editor.openContent(document.getContent());

        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        boolean shutdownRequest = false;
        while(!shutdownRequest) {
            displayCommands();
            String command = scanner.next();
            switch(command) {
                case "1":
                    System.out.println("[---METADATA---]");
                    System.out.println(document.getMetadata());
                    System.out.println("\n[---CONTENT----]");
                    AtomicInteger lineNumber = new AtomicInteger(0);
                    editor.readAllLines().stream()
                            .map(l -> String.format("%2d: %s", lineNumber.getAndIncrement(), l))
                            .forEach(System.out::println);
                    break;
                case "2":
                    System.out.println("\n[---EDIT LINE---]");
                    System.out.println("\nType line number: ");
                    int lineToUpdate = scanner.nextInt();
                    if (!(lineToUpdate >= 0 && lineToUpdate < editor.getCurrentLineRange().size())) {
                        System.out.println("ERROR:  Line number outside of content range");
                        break;
                    }
                    System.out.println("\nOld line value: ");
                    System.out.println(editor.readLine(lineToUpdate));
                    System.out.println("\nNew line value: ");
                    String newLine = scanner.next();
                    editor.updateLine(lineToUpdate, newLine);
                    break;
                case "3":
                    System.out.println("\n[---DELETE LINE---]");
                    System.out.println("\nType line number: ");
                    int lineToDelete = scanner.nextInt();
                    if (!(lineToDelete >= 0 && lineToDelete < editor.getCurrentLineRange().size())) {
                        System.out.println("ERROR:  Line number outside of content range");
                        break;
                    }
                    System.out.println("\nDo you realy want to delete this line [yes/no]: " + lineToDelete + ": " + editor.readLine(lineToDelete));
                    String decision = scanner.next();
                    if ("yes".equals(decision)) {
                        editor.deleteLineAtIndex(lineToDelete);
                        System.out.println("Line deleted");
                    }

                    break;
                case "4":
                    scanner.close();
                    shutdownRequest = true;
                default:

            }
        }
    }

    private static void displayCommands() {
        String help = "\n[------COMMANDS------]" +
                "\n\t1 : readAllLines" +
                "\n\t2 : editLine" +
                "\n\t3 : deleteLine" +
                "\n\t4 : exit\n";
        System.out.println(help);
    }

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
