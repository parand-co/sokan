package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.faces.context.FacesContext;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Excel<T> {

    private String path;
    private String fileName;
    private List<String> listColumn = new ArrayList<>();

    private Excel() {}

    public Excel(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    public Boolean create(String sheetName, String[] columnsName, List<T> employees){
        // Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setRightToLeft(true);

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < columnsName.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnsName[i]);
            cell.setCellStyle(headerCellStyle);
        }

//         Create Cell Style for formatting Date
//        CellStyle dateCellStyle = workbook.createCellStyle();
//        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        int rowNum = 1;


        for (T employee : employees) {
            Class<?> employeeClass = employee.getClass();

            Row row = sheet.createRow(rowNum++);

            if(listColumn.size() == 0) {
                Field[] fields = employeeClass.getDeclaredFields();
                for (Field field : fields) {
                    listColumn.add(field.getName());
                }
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
            String string = gson.toJson(employee, new TypeToken<T>(){}.getType());
            string = string.substring(1, string.length()-1);
            string = string.replaceAll("\n", "");
            string = string.replaceAll("\"", "");
            string = string.trim();

            String[] data = string.split(",");

            for (String source : data) {
                String[] split = source.split(":");

                row.createCell(listColumn.indexOf(split[0].trim()))
                        .setCellValue(split[1].trim());
            }


        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columnsName.length; i++) {
            sheet.autoSizeColumn(i);
        }

        String projectPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(path);

        // Write the output to a file
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(projectPath + "/" + fileName + ".xlsx");
            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
            workbook.close();
        } catch (IOException e) {
            return false;
        }


        return true;
    }
}