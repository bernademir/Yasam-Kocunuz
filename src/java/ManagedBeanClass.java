
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

@ManagedBean
@ApplicationScoped
public class ManagedBeanClass {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DOMAIN_NAME = "localhost";
    static final String DB_NAME = "programmingproje";
    static final String DB_URL = "jdbc:mysql://" + DOMAIN_NAME + "/" + DB_NAME;
    static final String USER = "root";
    static final String PASS = "";
    Connection conn = null;
    PreparedStatement psmt = null;
    String name;
    String surname;
    String kullaniciAdi;
    List<String> cinsiyet = new ArrayList<String>();
    String mail;
    String password;
    String adres;
    String girisKullaniciAdi;
    String girisSifre;
    String cinsiyet1;

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<String> getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(List<String> cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getGirisKullaniciAdi() {
        return girisKullaniciAdi;
    }

    public void setGirisKullaniciAdi(String girisKullaniciAdi) {
        this.girisKullaniciAdi = girisKullaniciAdi;
    }

    public String getGirisSifre() {
        return girisSifre;
    }

    public void setGirisSifre(String girisSifre) {
        this.girisSifre = girisSifre;
    }

    public String girisKontrol() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        String sql = "SELECT *FROM girisbilgileri";
        psmt = conn.prepareStatement(sql);
        ResultSet rs;
        rs = psmt.executeQuery();   //selectli sql querylerinde kullanılıyor bu
        while (rs.next()) {
            if (rs.getString("kullaniciAdi").equals(girisKullaniciAdi)) {
                if (rs.getString("sifre").equals(girisSifre)) {
                    return "anasayfa?faces-redirect=true";
                }
            }
        }
        rs.close();
        psmt.close();
        conn.close();
        return "index?faces-redirect=true";

    }

    public String yeniKayit() throws SQLException, ClassNotFoundException {
        if ("Kadın".equals(cinsiyet.get(0))) {
            cinsiyet1 = "Kadin";
        } else {
            cinsiyet1 = "Erkek";
        }
        int result1, result2;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        String sql1, sql2;
        sql1 = "INSERT INTO kullanicibilgileri(isim,soyisim,kullaniciAdi,cinsiyet,mail,adres)" + "VALUES(?,?,?,?,?,?)";
        psmt = conn.prepareStatement(sql1);
        psmt.setString(1, name);
        psmt.setString(2, surname);
        psmt.setString(3, kullaniciAdi);
        psmt.setString(4, cinsiyet1);
        psmt.setString(5, mail);
        psmt.setString(6, adres);
        result1 = psmt.executeUpdate();  //insert into update ve deleteli sql querylerinde kullanılıyor

        sql2 = "INSERT INTO girisbilgileri(kullaniciAdi,sifre)" + "VALUES(?,?)";
        psmt = conn.prepareStatement(sql2);
        psmt.setString(1, kullaniciAdi);
        psmt.setString(2, password);
        result2 = psmt.executeUpdate();  //insert into update ve deleteli sql querylerinde kullanılıyor

        conn.close();
        psmt.close();

        if (result1 != -1) {     // eğer -1 ise bir sıkıntı var demektir.database e kayıt gerçekleşmemiş
            //sorun yok
            return "anasayfa?faces-redirect=true";
        }
        return "index?faces-redirect=true";
    }
    
   
}
