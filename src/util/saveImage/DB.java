package util.saveImage;

import amar.model.Personel;
import dataBaseModel.dao.PersonelDao;
import dataBaseModel.util.HibernateUtil;
import org.hibernate.Session;
import util.Pic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.List;

class DB {

    DB() {}

    Connection dbConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection("jdbc:sqlserver://172.16.34.214; databaseName =pwkara; user=mehrdad; password=Aa123456;");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    void getImageData(Connection connect) {
        byte[] fileByte;
        String query;
        try {
            query = "SELECT \n" +
                    "[PERS_NO], \n" +
                    "[Img]\n" +
                    "FROM\n" +
                    "[pwkara].[dbo].[EMPLOYEE]\n" +
                    "where [Img] is not null";

            Statement state = connect.createStatement();
            ResultSet rs = state.executeQuery(query);

            OutputStream targetFile = null;

            while (rs.next()) {
                String shp = 0 +String.valueOf(rs.getInt("PERS_NO"));
                fileByte = rs.getBytes("Img");
                targetFile = new FileOutputStream("D:\\image_new_out_prs_sokan\\" + shp + ".jpg");

                targetFile.write(fileByte);

                Session session = HibernateUtil.getSession();
                List<Personel> personels = session.createQuery("FROM Personel WHERE shomarePersoneli = ?").setString(0, shp).list();
                session.close();

                if(personels.size() > 0){
                    Personel personel = personels.get(0);

                    personel.setAxe("/../pic/avatars/" + shp + "_enc.jpg");

                    PersonelDao.getInstance().getBaseQuery().createOrUpdate(personel);
                }

                enc(shp);
            }
            assert targetFile != null;
            targetFile.close();
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void enc(String shp){
        File destionation = new File("D:\\image_new_out_prs_sokan\\"+shp+".jpg");
        File enc = new File("D:\\sokan\\out\\artifacts\\pic\\avatars\\"+shp+"_enc.jpg");

        Pic pic = new Pic();
        pic.encrypt(destionation, enc);
    }
}

