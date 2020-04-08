package util;

import dashboard.DashboardBean;
import org.primefaces.model.ByteArrayContent;
import util.crypto.CryptoException;
import util.crypto.CryptoUtils;

import javax.crypto.Cipher;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.*;
import java.util.Objects;

@ManagedBean
@ViewScoped
public class Pic implements Serializable{

    public ByteArrayContent dycriptPicture(String path) {
        if (path != null && !Objects.equals(path, "")) {
            ByteArrayContent bytes = getByteArrayContent(path);
            if (bytes != null) return bytes;
        } else {
            path = "/../pic/avatars/000000000_enc.jpg";
            ByteArrayContent bytes = getByteArrayContent(path);
            if (bytes != null) return bytes;
        }
        return null;
    }

    private ByteArrayContent getByteArrayContent(String path) {
        try {
            File encryptedFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(DashboardBean.READ_PATH).concat(path));
            assert (CryptoUtils.doCryptoD(Cipher.DECRYPT_MODE, DashboardBean.ENC_KEY, encryptedFile)) != null;
            InputStream inp = new ByteArrayInputStream((CryptoUtils.doCryptoD(Cipher.DECRYPT_MODE, DashboardBean.ENC_KEY, encryptedFile)));
            BufferedInputStream in = new BufferedInputStream(inp);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            int val;
            while ((val = in.read()) != -1) {
                out.write(val);
            }

            byte[] bytes = out.toByteArray();
            return new ByteArrayContent(bytes, "image/jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void encrypt(File target, File export) {
        try {
            CryptoUtils.encrypt(DashboardBean.ENC_KEY, target, export);
        } catch (CryptoException ex) {
            ex.printStackTrace();
        }
    }

    public ByteArrayContent loadBannerFromTable(String path){
        if(path != null && !Objects.equals(path, "")) {
            String url = "/../pic/banner/" + path;
            InputStream inp;
            try {
                inp = new FileInputStream(FacesContext.getCurrentInstance().getExternalContext().getRealPath(DashboardBean.READ_PATH).concat(url));
                BufferedInputStream in = new BufferedInputStream(inp);
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                int val;
                while ((val = in.read()) != -1) {
                    out.write(val);
                }
                byte[] bytes = out.toByteArray();
                return new ByteArrayContent(bytes, "image/jpg");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
