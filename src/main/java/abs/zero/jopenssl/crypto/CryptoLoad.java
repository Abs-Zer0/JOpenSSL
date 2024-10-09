package abs.zero.jopenssl.crypto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

abstract class CryptoLoad {

    static {
        String libsPath = System.getProperty("openssl.libs.path");
        String libFileName = null;
        if (libsPath != null && !libsPath.isBlank()) {
            try {
                Path libsDirectory = Path.of(libsPath);
                if (Files.isDirectory(libsDirectory)) {
                    Optional<Path> libFile = Files.list(libsDirectory)
                            .filter(path -> Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
                            .filter(file -> file.getFileName().toString().toLowerCase().contains("libcrypto"))
                            .findFirst();
                    if (libFile.isPresent()) {
                        libFileName = libFile.get().toString();
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace(System.out);
            }
        }

        if (libFileName != null) {
            System.out.println("load");
            System.load(libFileName);
        } else {
            System.out.println("loadLibrary");
            System.loadLibrary("libcrypto");
        }
    }

}
