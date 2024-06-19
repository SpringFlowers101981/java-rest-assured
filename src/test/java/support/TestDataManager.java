package support;

import io.restassured.RestAssured;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

import static org.codehaus.groovy.classgen.Verifier.getTimestamp;

public class TestDataManager {

    private static DateTimeFormatter formatter;
    private static final ThreadLocal<String> timestampThreadLocal = ThreadLocal.withInitial(() -> LocalDateTime.now().format(formatter));

    private static String getTimestamp() {
        return timestampThreadLocal.get();
    }

    public static Map<String, String> getRecruiterCredentialFromFile(){
        Map<String, Object>  restData = getMapFromYamlFile("rest_data");
        Map<String, String>  credentials = (Map<String, String>) restData.get("recruiter");
        return credentials;
    }

    public static Map<String, String> getPositionFromFile(String entityName, String fileName){
        // entityName ="automation", fileName = "rest_data"
        Map<String, Object>  restData = getMapFromYamlFile(fileName);
        Map<String, String> position = (Map<String, String>) restData.get(entityName);
        String title = position.get("title");
        title = title + "+" + getTimestamp();  // Jr Automation Engineer + 2024-06-10T12:00:00Z
        position.put("title", title);
//        System.out.println(restData);
        System.out.println(position);
        return position;
    }

    public static Map<String, String> getCandidateFromFile(String entityName, String fileName) {
        //  Make sure to make email unique, i.e. for john.doe@example.com => john.doe+TIMESTAMP@example.com. Hint, use .split("@")
        //entityName => junior_candidate, fileName => rest_data
        Map<String, Object> restData =  getMapFromYamlFile(fileName);
        Map<String, String> candidate = (Map<String, String>) restData.get(entityName);

        String[] emails = candidate.get("email").split("@");
        candidate.put("email", emails[0] + RandomData.getRandomString() + "@" + emails[1]);
        return  candidate;

    }


    public static Map<String, Object> getMapFromYamlFile(String fileName) {
        String path = System.getProperty("user.dir") + "/src/test/resources/data/" + fileName + ".yml";
        try (InputStream stream = new FileInputStream(path)) {
            Yaml yaml = new Yaml();
            return yaml.load(stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + path, e);
        } catch (IOException e) {
            throw new Error("Error reading the file", e);
        }
    }
}
