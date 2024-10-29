package abs.zero.jopenssl.ssl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class SSLLoad {

    protected static final Logger LOG = LoggerFactory.getLogger(SSLLoad.class);

    private static final String LIB_NAME = "libssl";

    static {
        String libsPath = System.getProperty("openssl.libs.path");
        String libFileName = null;
        if (libsPath != null && !libsPath.isBlank()) {
            try {
                Path libsDirectory = Path.of(libsPath);
                if (Files.isDirectory(libsDirectory)) {
                    String fileExt;
                    if (SystemUtils.IS_OS_WINDOWS) {
                        fileExt = ".dll";
                    } else if (SystemUtils.IS_OS_LINUX) {
                        fileExt = ".so";
                    } else {
                        throw new Exception("Unknown system");
                    }

                    try (Stream<Path> list = Files.list(libsDirectory)) {
                        Optional<Path> libFile = list
                                .filter(path -> Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
                                .filter(file -> file.getFileName().toString().toLowerCase().contains(LIB_NAME))
                                .filter(file -> file.getFileName().toString().toLowerCase().endsWith(fileExt))
                                .findFirst();
                        if (libFile.isPresent()) {
                            libFileName = libFile.get().toString();
                        }
                    }
                }
            } catch (IOException ioe) {
                LOG.error("", ioe);
            } catch (Exception e) {
                LOG.error("libFileName", e);
            }
        }

        if (libFileName != null) {
            System.load(libFileName);
        } else {
            System.loadLibrary(LIB_NAME);
        }
    }

}
