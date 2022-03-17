package com.pigumer.tools.download;

import org.junit.jupiter.api.Test;

public class DownloadTest {

    @Test
    public void downloadTest() throws Exception {
        Download download = new Download();
        download.download("build.yml");
    }
}
