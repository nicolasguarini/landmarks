package bicocca2023.assignment3;

import bicocca2023.assignment3.controller.UserController;
import bicocca2023.assignment3.util.ApiTestUtils;
import bicocca2023.assignment3.util.PersistenceManager;
import org.junit.jupiter.api.*;
import spark.Spark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {
    private static final String BASE_URL = "/api/users";
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
            ApiTestUtils.request("DELETE", BASE_URL + "/" + testId, null);
            testId = null;
        }
    }

    @Test
    public void testUsersRead() {
        ApiTestUtils.TestResponse res = ApiTestUtils.request("GET", BASE_URL, null);

        assertNotNull(res);
        assertEquals(200, res.status);
    }

    @Test
    public void testUserCreate() {
        String testPostUrl = BASE_URL + "?username=" + testUsername;
        String testGetUrl = BASE_URL + "/";

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
    public void testVipUserCreate() {
        String testUsername = UserTest.testUsername;
        if(UserTest.testUsername != null)
            testUsername = UserTest.testUsername + "_vip";

        String testPostUrl = BASE_URL + "?username=" + testUsername + "&type=VIP";
        String testGetUrl = BASE_URL + "/";

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
        String testPostUrl = BASE_URL + "?username=" + testUsername;
        String testPutUrl = BASE_URL + "/";
        String testGetUrl = BASE_URL + "/";

        ApiTestUtils.TestResponse resPost = ApiTestUtils.request("POST", testPostUrl, null);

        assertNotNull(resPost);
        assertEquals(201, resPost.status);

        testId = (String) resPost.json().get("id");
        testPutUrl += "/" + testId + "/update?username=" + testUsername + "_updated";
        testGetUrl += testId;

        ApiTestUtils.TestResponse resPut = ApiTestUtils.request("PUT", testPutUrl, null);
        assertNotNull(resPut);
        assertEquals(200, resPut.status);

        ApiTestUtils.TestResponse resGet = ApiTestUtils.request("GET", testGetUrl, null);
        assertNotNull(resGet);
        assertEquals(testUsername + "_updated", resGet.json().get("username"));
    }

    @Test
    public void testUserDelete() {
        String testPostUrl = BASE_URL + "?username=" + testUsername;
        String testDelUrl = BASE_URL + "/";

        ApiTestUtils.TestResponse resPost = ApiTestUtils.request("POST", testPostUrl, null);
        assertNotNull(resPost);
        assertEquals(201, resPost.status);
        testId = (String) resPost.json().get("id");

        testDelUrl += testId;
        ApiTestUtils.TestResponse resDel = ApiTestUtils.request("DELETE", testDelUrl, null);
        assertNotNull(resDel);
        assertEquals(200, resDel.status);

        ApiTestUtils.TestResponse resGet = ApiTestUtils.request("GET", BASE_URL + "/" + testId, null);
        assertNotNull(resGet);
        assertEquals(410, resGet.status);
    }
}
