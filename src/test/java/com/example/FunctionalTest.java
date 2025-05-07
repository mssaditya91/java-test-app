package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FunctionalTest {

    private static int testPort;

    @BeforeAll
    static void setUp() throws Exception {
        testPort = getFreePort();
        port(testPort);
        get("/", (req, res) -> "Hello, Java App!");
        init();
        waitForPortToOpen(testPort, 10000);
    }

    @AfterAll
    static void tearDown() {
        stop();
        awaitStop();
    }

    @Test
    @Order(1)
    void testRootEndpoint() throws Exception {
        URL url = new URL("http://localhost:" + testPort + "/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        assertEquals(200, responseCode, "Should get HTTP 200");
        assertEquals("Hello, Java App!", content.toString(), "Should return expected message");
    }

    // Wait for the server port to be open
    private static void waitForPortToOpen(int port, int timeoutMs) throws Exception {
        long start = System.currentTimeMillis();
        Exception lastException = null;
        while (System.currentTimeMillis() - start < timeoutMs) {
            try (Socket socket = new Socket("localhost", port)) {
                return; // Port is open
            } catch (Exception e) {
                lastException = e;
                Thread.sleep(100);
            }
        }
        throw new RuntimeException("Timeout waiting for port " + port + " to open. Last exception: " + lastException);
    }

    // Get a random free port
    private static int getFreePort() throws Exception {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }
}
