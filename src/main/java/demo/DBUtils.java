package demo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
    private static Connection connection = null;  //statine is kitu klsiu nepasieksiu, o tik per metodus
    public static Connection getConnection(){  //regis cia nereikes uzdarineti, nes anksciau metoduose kreipdavomes ir po veiksmo uzdarydavome,
        // o cia visos klases kreipsis per viena konekcija
        if(connection!=null){  //cia tikrinam ar musu konekcija egzistuoja ar ne, jei nera, tai su else sukuriame, jei yra, tai naudojameta, kuri yra pas mus per website pasikurta
            return connection;
        }
        else{
            try {
                Properties properties = new Properties();
                //nuskaitysim faila
                //naudojam, nes is pradziu java nezino, kur nuskaityti faila, tad DBUTILS.class. parodo this, kad cia as esu, kad sitame pakete ieskot db.properties
                InputStream inputStream = DBUtils.class.getClassLoader().getResourceAsStream("db.properties");
                //uzkrausim inputStream
                properties.load(inputStream);

                String driver = properties.getProperty("driver"); //cia apsirasom db.properties faile
                // cia skirta labiau administratoriams, nes jiems reikia pasikeisti db.properties serverio duomenis (ten pakeitus, pasikeicia kode
                //nes administratoriais nera programuotojai ir nera taip lengva ieskoti kode, jei dar ilgas kodas
                String url = properties.getProperty("url");
                String username = properties.getProperty("username");
                String password = properties.getProperty("password");

                //prisijungimas prie duomenu bazes
                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);

            }
            catch (ClassNotFoundException e){
                System.out.println("ClassNotFoundException klaida " + e);
            }
            catch (IOException e){
                System.out.println("IOExeption klaida " + e);
            } catch (SQLException e) {
                System.out.println("SQLException klaida " + e);
            }
        }

        return connection;
    }



}
