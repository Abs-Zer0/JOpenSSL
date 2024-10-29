package abs.zero.jopenssl;

import abs.zero.jopenssl.crypto.OpenSSL;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    private static final Logger LOG = LoggerFactory.getLogger(MainTest.class);

    @Test
    public void multiThreadTest() {
        LOG.info("START");
        List<Integer> types = Arrays.asList(
                OpenSSL._VERSION,
                OpenSSL._CFLAGS,
                OpenSSL._BUILT_ON,
                OpenSSL._PLATFORM,
                OpenSSL._DIR,
                OpenSSL._ENGINES_DIR,
                OpenSSL._VERSION_STRING,
                OpenSSL._FULL_VERSION_STRING,
                OpenSSL._MODULES_DIR,
                OpenSSL._CPU_INFO,
                OpenSSL._WINCTX);
        String res = types.parallelStream()
                .map(OpenSSL::_version)
                .sorted()
                .collect(Collectors.joining(","));
        LOG.info("RES = {}", res);
        assertEquals("ENGINESDIR: N/A,LibreSSL 3.8.2,OPENSSLDIR: \"C:/Windows/libressl/ssl\",built on: date not available,compiler: information not available,not available,not available,not available,not available,not available,platform: information not available", res);

        assertEquals(10, 5 + 5);
        LOG.info("FINISH");
    }

}
