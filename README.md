# Landmarks

This project focuses on developing the backend of a social application for saving points of interest (*landmarks*) around the world.
Each user can save points of interest which include the coordinates (latitude, longitude, altitude), a name, and the description relating to that point.
A user can also "follow" other users, in order to see (via an hypothetical frontend which can be a web app or a mobile app) the points of interest that they save at any time.
Users are divided into Basic and VIP. Basic users have a limit of 10 landmarks saves, while VIPs have unlimited saves.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java:** Install Java Development Kit (JDK) on your system. You can download it from [Oracle's website](https://www.oracle.com/java/technologies/javase-downloads.html) or use [OpenJDK](https://adoptopenjdk.net/).
- **Maven:** Install Maven to manage the project's build and dependencies. You can download Maven from [here](https://maven.apache.org/download.cgi) and follow the [installation instructions](https://maven.apache.org/install.html).
- **PostgreSQL:** Set up a PostgreSQL database. You can download it from [the official website](https://www.postgresql.org/download/) or use a cloud-based solution like [PostgreSQL on AWS](https://aws.amazon.com/rds/postgresql/) or [Supabase](https://supabase.com/).

## Installation

1. **Clone the Repository:**
    ```bash
    git clone https://gitlab.com/bicocca_projects/2023_assignment3_landmarks
    ```
   

2. **Navigate to Project Directory:**
    ```bash
    cd 2023_assignment3_landmarks
    ```

3. **Configure Database Credentials:**
    - Locate the `persistence.example.xml` file in the `src/main/resources/META-INF` directory.
    - Open the file and provide your PostgreSQL database credentials.
    - Save the file.


4. **Rename the Configuration File:**
    - Rename `persistence.example.xml` to `persistence.xml`.


5. **Build the Project:**
    ```bash
    mvn clean install
    ```

## Execution

1. **Run the Application:**
   - Using an IDE (e.g., IntelliJ IDEA or Eclipse):

      - Open the project in your IDE.
      - Locate the `Main.java` file in the `main` package.
      - Run the main class.

   - Using the command line:

      ```bash
      java -jar target/Landmarks-1.0-SNAPSHOT.jar
      ```

2. **Access the API:**
    - Open your web browser and go to [http://localhost:8000](http://localhost:8000) or use your preferred API client, such as [Postman](https://www.postman.com/) or [Insomnia](https://insomnia.rest/).

## Testing

1. **Run JUnit Tests:**
    ```bash
    mvn test
    ```

2. **Review Test Results:**
    - Check the console output for test results.

## Additional Notes

- Ensure that Java, Maven, and PostgreSQL are properly installed and configured.
- Make sure the PostgreSQL server and database are running and accessible.

## API Usage

### User Routes
Create a User:
- **Route:** `POST /api/users`
- **Parameters:**
  - `username`

Get All Users:
- **Route:** `GET /api/users`
- **Parameters:** None.

Get User by ID:
- **Route:** `GET /api/users/:id`
- **Parameters:** None

Update User:
- **Route:** `PUT /api/users/:id/update`
- **Parameters:**
   - `username`

Delete User:
- **Route:** `DELETE /api/users/:id`
- **Parameters:** None

Get VIP Users:
- **Route:** `GET /api/users/vip`
- **Parameters:** None.

Get Basic Users:
- **Route:** `GET /api/users/basic`
- **Parameters:** None.

Follow User:
- **Route:** `POST /api/users/:id/follow/:idToFollow`
- **Parameters:** None

Unfollow User:
- **Route:** `POST /api/users/:id/unfollow/:idToUnfollow`
- **Parameters:** None

Get Followers:
- **Route:** `GET /api/users/:id/followers`
- **Parameters:** None

Get Followings:
- **Route:** `GET /api/users/:id/followings`
- **Parameters:** None

Get User Landmarks:
- **Route:** `GET /api/users/:id/landmarks`
- **Parameters:** None

Get Popular Users:
- **Route:** `GET /api/users/queries/most-populars`
- **Parameters:** None.

### Landmark Routes
Create a Landmark:
- **Route:** `POST /api/landmarks`
- **Parameters:**
   - Request Body: Landmark details (e.g., name, description).

Get All Landmarks:
- **Route:** `GET /api/landmarks`
- **Parameters:** None.

Get Landmark by ID:
- **Route:** `GET /api/landmarks/:id`
- **Parameters:** None

Update Landmark:
- **Route:** `PUT /api/landmarks/:id/update`
- **Parameters:**
   - `userid`
   - `name`
   - `lat`
   - `long`

Delete Landmark:
- **Route:** `DELETE /api/landmarks/:id`
- **Parameters:** None