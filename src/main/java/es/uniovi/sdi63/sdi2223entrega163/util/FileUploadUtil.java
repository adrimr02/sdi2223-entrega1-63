package es.uniovi.sdi63.sdi2223entrega163.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void saveFile(String uploadPath, String filename, MultipartFile file) throws IOException {

        Path path = Paths.get( uploadPath );

        if (!Files.exists(path)) {
            Files.createDirectories( path );
        }

        try ( InputStream inputStream = file.getInputStream() ) {
            Path filepath = path.resolve( filename );
            Files.copy( inputStream, filepath, StandardCopyOption.REPLACE_EXISTING );
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + filename, ioe);
        }

    }

    public static void deleteFile(String path) throws IOException {

        Path filePath = Paths.get( path );

        try {
            Files.deleteIfExists( filePath );
        } catch (IOException ioe) {
            throw new IOException("Could not delete image: " + path, ioe);
        }

    }

}
