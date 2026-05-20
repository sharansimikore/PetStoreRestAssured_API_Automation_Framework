package api.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.DataProvider;

import api.config.ConfigManager;

public class DataProviders {

    private final String excelPath;

    public DataProviders() {
        this.excelPath = resolveTestDataPath();
    }

    @DataProvider(name = "data")
    public String[][] getAllData() throws IOException {
        XLUtility xl = new XLUtility(excelPath);

        int rowNum = xl.getRowCount("Sheet1");
        int colCount = xl.getCellCount("Sheet1", 1);

        String[][] apiData = new String[rowNum][colCount];

        for (int i = 1; i <= rowNum; i++) {
            for (int j = 0; j < colCount; j++) {
                apiData[i - 1][j] = xl.getCellData("Sheet1", i, j);
            }
        }

        return apiData;
    }

    @DataProvider(name = "userName")
    public Object[][] getUserNames() throws IOException {
        XLUtility xl = new XLUtility(excelPath);

        int rows = xl.getRowCount("Sheet1");
        Object[][] data = new Object[rows][1];

        for (int i = 1; i <= rows; i++) {
            data[i - 1][0] = xl.getCellData("Sheet1", i, 1);
        }

        return data;
    }

    private static String resolveTestDataPath() {
        ConfigManager.loadEnvironment();
        String configuredPath = ConfigManager.getTestDataPath();

        Path classpathFile = Paths.get("src", "test", "resources", configuredPath);
        if (Files.exists(classpathFile)) {
            return classpathFile.toAbsolutePath().toString();
        }

        try (InputStream stream = DataProviders.class.getClassLoader()
                .getResourceAsStream(configuredPath)) {
            if (stream != null) {
                Path temp = Files.createTempFile("testData", ".xlsx");
                Files.copy(stream, temp, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                temp.toFile().deleteOnExit();
                return temp.toString();
            }
        } catch (IOException ignored) {
            // fall through to legacy path
        }

        return Paths.get(System.getProperty("user.dir"), "testData", "testData.xlsx")
                .toAbsolutePath()
                .toString();
    }
}
