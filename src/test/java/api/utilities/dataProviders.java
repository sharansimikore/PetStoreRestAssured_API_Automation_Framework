package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class dataProviders {

    String path = System.getProperty("user.dir")
            + "//testData//testData.xlsx";

    // ======================================================
    // Get ALL data
    // ======================================================

    @DataProvider(name = "data")
    public String[][] getAllData() throws IOException {

        XLUtility xl = new XLUtility(path);

        int rowNum = xl.getRowCount("Sheet1");
        int colCount = xl.getCellCount("Sheet1", 1);

        String apiData[][] = new String[rowNum][colCount];

        for (int i = 1; i <= rowNum; i++) {

            for (int j = 0; j < colCount; j++) {

                apiData[i - 1][j] =
                        xl.getCellData("Sheet1", i, j);
            }
        }

        return apiData;
    }

    // ======================================================
    // Get data by USERNAME
    // ======================================================

    @DataProvider(name = "userName")
    public Object[][] getUserNames() throws IOException {

        XLUtility xl = new XLUtility(path);

        int rows = xl.getRowCount("Sheet1");

        Object data[][] = new Object[rows][1];

        for (int i = 1; i <= rows; i++) {

            data[i - 1][0] =
                    xl.getCellData("Sheet1", i, 1);
        }

        return data;
    }    
    
}
