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
    public static final String BASE_URL = "/api/landmarks";

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
        assertNotNull(res);
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
    public void testLandmarkRead() {
        ApiTestUtils.TestResponse res = ApiTestUtils.request("GET", BASE_URL, null);
        assertNotNull(res);
        assertEquals(200, res.status);
    }

    @Test
    public void testLandmarkCreate() {
        String postUrl = BASE_URL + "?userid=" + testId + "&lat=100&long=100&name=mylandmark";
        ApiTestUtils.TestResponse res = ApiTestUtils.request("POST", postUrl, null);

        assertNotNull(res);
        String landmarkId = (String) res.json().get("id");
        assertEquals(201, res.status);

        String getUrl = BASE_URL + "/" + landmarkId;
        res = ApiTestUtils.request("GET", getUrl, null);

        assertNotNull(res);
        assertEquals("mylandmark", res.json().get("name"));
        assertEquals("{longitude=100.0, latitude=100.0}", res.json().get("coordinate").toString());
    }

    @Test
    public void testLandmarkDelete() {
        String postUrl = BASE_URL + "?userid=" + testId + "&lat=100&long=100&name=mylandmark";
        ApiTestUtils.TestResponse res = ApiTestUtils.request("POST", postUrl, null);

        assertNotNull(res);
        String landmarkId = (String) res.json().get("id");
        assertEquals(201, res.status);

        String getUrl = BASE_URL + "/" + landmarkId;
        res = ApiTestUtils.request("GET", getUrl, null);

        assertNotNull(res);
        assertEquals(200, res.status);

        String delUrl = BASE_URL + "/" + landmarkId;
        res = ApiTestUtils.request("DELETE", delUrl, null);

        assertNotNull(res);
        assertEquals(200, res.status);

        res = ApiTestUtils.request("GET", delUrl, null);

        assertNotNull(res);
        assertEquals(404, res.status);
    }

    @Test
    public void testLandmarkLimit() {
        String postUrl = BASE_URL + "?userid=" + testId + "&lat=100&long=100&name=mylandmark";
        ApiTestUtils.TestResponse res;

        for(int i = 0; i < BasicPlanUser.MAX_LANDMARKS; i++)
            ApiTestUtils.request("POST", postUrl, null);

        res = ApiTestUtils.request("POST", postUrl, null);

        assertNotNull(res);
        assertEquals(400, res.status);
    }

    @Test
    public void testLandmarkUpdate() {
        String postUrl = BASE_URL + "?userid=" + testId + "&lat=100&long=100&name=mylandmark";
        ApiTestUtils.TestResponse res = ApiTestUtils.request("POST", postUrl, null);

        assertNotNull(res);
        String landmarkId = (String) res.json().get("id");
        assertEquals(201, res.status);

        String updateUrl = BASE_URL + "/" + landmarkId + "/update?name=myupdatedlandmark&lat=123&description=updateddescription";
        ApiTestUtils.TestResponse resUpdate = ApiTestUtils.request("PUT", updateUrl, null);

        assertNotNull(resUpdate);
        HashMap body = resUpdate.json();
        assertEquals("myupdatedlandmark", body.get("name"));
        assertEquals("updateddescription", body.get("description"));
        assertEquals("{longitude=100.0, latitude=123.0}", body.get("coordinate").toString());
    }
}
