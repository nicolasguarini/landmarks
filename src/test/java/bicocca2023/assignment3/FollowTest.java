package bicocca2023.assignment3;

import bicocca2023.assignment3.controller.UserController;
import bicocca2023.assignment3.util.ApiTestUtils;
import bicocca2023.assignment3.util.PersistenceManager;
import org.junit.jupiter.api.*;
import spark.Spark;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FollowTest {
    private static final String BASE_URL = "/api/users";
    public static String testUsername1, testUsername2;
    public static String testId1, testId2;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        testUsername1 = ApiTestUtils.generateUniqueUsername();
        Thread.sleep(1001); // wait a second for unique timestamp-based usernames
        testUsername2 = ApiTestUtils.generateUniqueUsername();

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

    @BeforeEach
    public void createTestUsers() {
        ApiTestUtils.TestResponse res1 = ApiTestUtils.request("POST", BASE_URL + "?username=" + testUsername1, null);
        ApiTestUtils.TestResponse res2 = ApiTestUtils.request("POST", BASE_URL + "?username=" + testUsername2, null);

        assertNotNull(res1);
        assertNotNull(res2);
        assertEquals(201, res1.status);
        assertEquals(201, res2.status);

        testId1 = (String) res1.json().get("id");
        testId2 = (String) res2.json().get("id");
    }

    @AfterEach
    public void deleteTestUsers() {
        if (testId1 != null && testId2 != null) {
            ApiTestUtils.request("DELETE", BASE_URL + "/" + testId1, null);
            ApiTestUtils.request("DELETE", BASE_URL + "/" + testId2, null);
            testId1 = null;
            testId2 = null;
        }
    }

    @Test
    public void testFollower() {
        ApiTestUtils.TestResponse resPost = ApiTestUtils.request("POST", BASE_URL + "/" + testId1 + "/follow/" + testId2, null);

        assertNotNull(resPost);
        assertEquals(resPost.status, 200);

        ApiTestUtils.TestResponse resGet = ApiTestUtils.request("GET", BASE_URL + "/" + testId2 + "/followers", null);

        assertNotNull(resGet);
        assertEquals(resPost.status, 200);

        List<HashMap> followers = resGet.jsonList();

        assertEquals(1, followers.size());
        assertEquals(testUsername1, followers.get(0).get("username"));
        assertEquals(testId1, followers.get(0).get("id"));
    }

    @Test
    public void testFollowing() {
        ApiTestUtils.TestResponse resPost = ApiTestUtils.request("POST", BASE_URL + "/" + testId1 + "/follow/" + testId2, null);

        assertNotNull(resPost);
        assertEquals(resPost.status, 200);

        ApiTestUtils.TestResponse resGet = ApiTestUtils.request("GET", BASE_URL + "/" + testId1 + "/followings", null);

        assertNotNull(resGet);
        assertEquals(resPost.status, 200);

        List<HashMap> followers = resGet.jsonList();

        assertEquals(1, followers.size());
        assertEquals(testUsername2, followers.get(0).get("username"));
        assertEquals(testId2, followers.get(0).get("id"));
    }

    @Test
    public void testUnfollow() {
        ApiTestUtils.TestResponse resPost = ApiTestUtils.request("POST", BASE_URL + "/" + testId1 + "/follow/" + testId2, null);

        assertNotNull(resPost);
        assertEquals(resPost.status, 200);

        resPost = ApiTestUtils.request("POST", BASE_URL + "/" + testId1 + "/unfollow/" + testId2, null);

        assertNotNull(resPost);
        assertEquals(200, resPost.status);

        ApiTestUtils.TestResponse resGet = ApiTestUtils.request("GET", BASE_URL + "/" + testId1 + "/followings", null);

        assertNotNull(resGet);
        assertEquals(200, resPost.status);
        assertEquals(0, resGet.jsonList().size());
    }

    @Test
    public void testAlreadyFollowingError() {
        ApiTestUtils.TestResponse resPost = ApiTestUtils.request("POST", BASE_URL + "/" + testId1 + "/follow/" + testId2, null);

        assertNotNull(resPost);
        assertEquals(resPost.status, 200);

        resPost = ApiTestUtils.request("POST", BASE_URL + "/" + testId1 + "/follow/" + testId2, null);

        assertNotNull(resPost);
        assertEquals(400, resPost.status);
    }

    @Test
    public void testNotFollowingError() {
        ApiTestUtils.TestResponse resPost = ApiTestUtils.request("POST", BASE_URL + "/" + testId1 + "/follow/" + testId2, null);

        assertNotNull(resPost);
        assertEquals(resPost.status, 200);

        resPost = ApiTestUtils.request("POST", BASE_URL + "/" + testId2 + "/unfollow/" + testId1, null);

        assertNotNull(resPost);
        assertEquals(400, resPost.status);
    }
}
