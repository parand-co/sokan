package util.saveImage;

import java.sql.Connection;

public class SaveImage {

    public static void main(String[] args) {
        DB db = new DB();
        Connection connection = db.dbConnection();
        db.getImageData(connection);
    }
}