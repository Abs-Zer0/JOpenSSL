package abs.zero.jopenssl.ssl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Optional;

abstract class SSLLoad {

    protected static final org.slfj.Logger LOG = LoggerFactory.getLogger(SSLLoad.class);

    static {
        String libsPath = System.getProperty("openssl.libs.path");
        String libFileName = null;
        if (libsPath != null && !libsPath.isBlank()) {
            try {
                Path libsDirectory = Path.of(libsPath);
                if (Files.isDirectory(libsDirectory)) {
                    Optional<Path> libFile = Files.list(libsDirectory)
                            .filter(path -> Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
                            .filter(file -> file.getFileName().toString().toLowerCase().contains("libssl"))
                            .findFirst();
                    if (libFile.isPresent()) {
                        libFileName = libFile.get().toString();
                    }
                }
            } catch (IOException ioe) {
                LOG.error("", ioe);
            }
        }

        if (libFileName != null) {
            System.load(libFileName);
        } else {
            System.loadLibrary("libssl");
        }
    }

}
