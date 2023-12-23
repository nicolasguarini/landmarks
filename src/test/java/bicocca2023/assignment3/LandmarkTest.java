package bicocca2023.assignment3;

import bicocca2023.assignment3.controller.LandmarkController;
import bicocca2023.assignment3.controller.UserController;
import bicocca2023.assignment3.model.user.BasicPlanUser;
import bicocca2023.assignment3.util.ApiTestUtils;
import bicocca2023.assignment3.util.PersistenceManager;
import org.junit.jupiter.api.*;
import spark.Spark;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class LandmarkTest {
    public static String testUsername;
    public static String testId;
    String baseUrl = "/api/landmarks";

    @BeforeAll
    public static void setUp() {
        testUsername = ApiTestUtils.generateUniqueUsername();
        PersistenceManager.initialize();
        Spark.init();
        Routes.establishUserRoutes(new UserController());
        Routes.establishLandmarkRoutes(new LandmarkController());
        Spark.awaitInitialization();
    }

    @AfterAll
    public static void tearDown() {
        Spark.stop();
        PersistenceManager.close();
    }

    @BeforeEach
    public void createTestUser() {
        ApiTestUtils.TestResponse res = ApiTestUtils.request("POST", "/api/users?username=" + testUsername, null);
        testId = (String) res.json().get("id");
    }

    @AfterEach
    public void deleteTestUser() {
        if (testId != null) {
            ApiTestUtils.request("DELETE", "/api/users/" + testId, null);
            testId = null;
        }
    }

    @Test
    public void testLandmarkGET() {
        ApiTestUtils.TestResponse res = ApiTestUtils.request("GET", baseUrl, null);
        assertNotNull(res);
        assertEquals(200, res.status);
    }

    @Test
    public void testLandmarkPOST() {
        String postUrl = baseUrl + "?userid=" + testId + "&lat=100&long=100&name=mylandmark";
        ApiTestUtils.TestResponse res = ApiTestUtils.request("POST", postUrl, null);
        String landmarkId = (String) res.json().get("id");

        assertNotNull(res);
        assertEquals(201, res.status);

        String getUrl = baseUrl + "/" + landmarkId;
        res = ApiTestUtils.request("GET", getUrl, null);

        assertNotNull(res);
        HashMap body = res.json();
        assertEquals("mylandmark", body.get("name"));
        assertEquals("{longitude=100.0, latitude=100.0}", body.get("coordinate").toString());
    }

    @Test
    public void testLandmarkDELETE() {
        String postUrl = baseUrl + "?userid=" + testId + "&lat=100&long=100&name=mylandmark";
        ApiTestUtils.TestResponse res = ApiTestUtils.request("POST", postUrl, null);
        String landmarkId = (String) res.json().get("id");

        assertNotNull(res);
        assertEquals(201, res.status);

        String getUrl = baseUrl + "/" + landmarkId;
        res = ApiTestUtils.request("GET", getUrl, null);

        assertNotNull(res);
        assertEquals(200, res.status);

        String delUrl = baseUrl + "/" + landmarkId;
        res = ApiTestUtils.request("DELETE", delUrl, null);

        assertNotNull(res);
        assertEquals(200, res.status);

        res = ApiTestUtils.request("GET", delUrl, null);

        assertNotNull(res);
        assertEquals(404, res.status);
    }

    @Test
    public void testLandmarkLimit() {
        String postUrl = baseUrl + "?userid=" + testId + "&lat=100&long=100&name=mylandmark";
        ApiTestUtils.TestResponse res;

        for(int i = 0; i < BasicPlanUser.MAX_LANDMARKS; i++)
            res = ApiTestUtils.request("POST", postUrl, null);

        res = ApiTestUtils.request("POST", postUrl, null);

        assertNotNull(res);
        assertEquals(400, res.status);
    }
}
