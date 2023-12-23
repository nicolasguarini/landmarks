package bicocca2023.assignment3;

import bicocca2023.assignment3.controller.UserController;
import bicocca2023.assignment3.util.ApiTestUtils;
import bicocca2023.assignment3.util.PersistenceManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    public static String testUsername;
    public static String testId;

    @BeforeAll
    public static void setUp() {
        testUsername = ApiTestUtils.generateUniqueUsername();
        PersistenceManager.initialize();
        Spark.init();
        Routes.establishUserRoutes(new UserController());
        Spark.awaitInitialization();
    }

    @AfterAll
    public static void tearDown() {
        Spark.stop();
        PersistenceManager.close();
    }

    @AfterEach
    public void deleteTestUser() {
        if (testId != null) {
            ApiTestUtils.request("DELETE", "/api/users/" + testId, null);
            testId = null;
        }
    }

    @Test
    public void testUsersGET() {
        String testUrl = "/api/users";
        ApiTestUtils.TestResponse res = ApiTestUtils.request("GET", testUrl, null);

        assertNotNull(res);
        assertEquals(200, res.status);
    }

    @Test
    public void testUserPOST() {
        String testPostUrl = "/api/users?username=" + testUsername;
        String testGetUrl = "/api/users/";

        ApiTestUtils.TestResponse resPost = ApiTestUtils.request("POST", testPostUrl, null);

        assertNotNull(resPost);
        assertEquals(201, resPost.status);

        testId = (String) resPost.json().get("id");
        testGetUrl += testId;
        ApiTestUtils.TestResponse resGet = ApiTestUtils.request("GET", testGetUrl, null);

        assertNotNull(resGet);
        assertEquals(200, resGet.status);
        assertEquals(testUsername, resGet.json().get("username"));
        assertEquals("BASIC", resGet.json().get("plan"));
    }

    @Test
    public void testVipUserPOST() {
        String testPostUrl = "/api/users?username=" + testUsername + "&type=VIP";
        String testGetUrl = "/api/users/";

        ApiTestUtils.TestResponse resPost = ApiTestUtils.request("POST", testPostUrl, null);
        assertNotNull(resPost);
        assertEquals(201, resPost.status);

        testId = (String) resPost.json().get("id");
        testGetUrl += testId;
        ApiTestUtils.TestResponse resGet = ApiTestUtils.request("GET", testGetUrl, null);

        assertNotNull(resGet);
        assertEquals(200, resGet.status);
        assertEquals(testUsername, resGet.json().get("username"));
        assertEquals("VIP", resGet.json().get("plan"));
    }

    @Test
    public void testUserUpdate() {
        String testPostUrl = "/api/users?username=" + testUsername;
        String testPutUrl = "/api/users/";
        String testGetUrl = "/api/users/";

        ApiTestUtils.TestResponse resPost = ApiTestUtils.request("POST", testPostUrl, null);

        assertNotNull(resPost);
        assertEquals(201, resPost.status);

        testId = (String) resPost.json().get("id");
        testPutUrl += testId + "?username=" + testUsername + "_updated";
        testGetUrl += testId;

        ApiTestUtils.TestResponse resPut = ApiTestUtils.request("PUT", testPutUrl, null);
        assertNotNull(resPut);
        assertEquals(200, resPut.status);

        ApiTestUtils.TestResponse resGet = ApiTestUtils.request("GET", testGetUrl, null);
        assertNotNull(resGet);
        assertEquals(testUsername + "_updated", resGet.json().get("username"));
    }
}
