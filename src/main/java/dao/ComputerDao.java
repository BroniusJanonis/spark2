package dao;

import demo.Computer;
import demo.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComputerDao {

    private Connection connection;

    // konstruktoriuje kiekvienam objektui jau pakursim prisijungima prie DB
    public ComputerDao() {
        connection = DBUtils.getConnection();
    }

    //select, insert, delete, update
    // Selectam naudojami createStatement() [uzklausa paruosi > selektam] ir executeQuery() [nes executini uzklausa > selekta]
    //      > preparedStatement() select'ams irgi reikia, kai norim gauti by Id ar pan.
    // Update, Delete, Add naudojami preparedStatement() [paselekcint, ka nori siust i serveri] ir executeUpdate() [nes keiti serveryje]

    public List<Computer> getAllComputer() {  //metodas gauti lista visu Student duomenu tipo objektu (Student savyje tures visa info)

        List<Computer> list = new ArrayList<>();
        try {
            String selectall = "select id, name, type, info, speed from computer";
            // pasikuriam statement savo konekcijai, kuria pakursime
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectall);  //resultset, nes gaunam duomenis ir juos desim i lista Student duomenu tipo
            while (rs.next()) { //kol gautas atsakymas is serverio turi duomeni > id, name, surname... todel tesiam deliojima i student objekta
                Computer student = new Computer(); //sukuriam student objekta Student duomenu tipo (Student konstruktoriuje aprasyta, kokius turime duomenis)
                student.setId(rs.getInt("id")); //i student objekta, kuris kolkas yra tuscias, setinam duomenis,
                //Kuris ResultSet rs galimas igauti per getInt("id" <atitikmuo) metoda
                student.setName(rs.getString("name"));
                student.setType(rs.getString("type"));
                student.setInfo(rs.getString("info"));
                student.setSpeed(rs.getInt("speed"));
                list.add(student); //sudedam student objekta i lista
            }
        } catch (SQLException e) {
            System.out.println("SQLException klaida " + e);
        }
        return list;
    }

    public void addComputer(Computer comp) {  //cia StudentControler.java apsirasem StudUser duomenu tipo parametra user, kurio ir prrasom parametre
        String insertsql = "INSERT INTO computer" +
                "(name, type, info, speed) " +
                "VALUES (?,?,?,?);";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(insertsql);
            //getId nereikia, nes kuriant nauja irasa serveryje, id priskiriamas automatiskai
            preparedStatement.setString(1, comp.getName()); //cia jau is web prisiskyrem prie konstruktoriaus kintamojo ir sukurem user
            //objekta. Cia mes is objekta isiimam atitinkama duomeni ir paruosiam siusti i serveri
            preparedStatement.setString(2, comp.getType());
            preparedStatement.setString(3, comp.getInfo());
            preparedStatement.setInt(4, comp.getSpeed());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("SQLException klaida " + e);
        }
    }

    public Computer getComputerById(int id) { //pagal id gaunam visa eilute
        Computer student = new Computer();
        try {
            String selectstudent = "SELECT * from computer where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectstudent);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                //cia turi atitikt pagal serveri "id", "name"..etc
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setType(rs.getString("type"));
                student.setInfo(rs.getString("info"));
                student.setSpeed(rs.getInt("speed"));
            }
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println("SQLException klaida " + e);
        }
        return student;
    }

    // su CTRL+F6 gali refactorint pasirinkta parametra/ zodi
    public void updatestudent(Computer computer) {  //apasirasom update, kad pagal id updatins
        //klaustukai ne kabutese turi but
        String updatesql = "UPDATE computer SET name = ?, type = ?, info= ?, speed= ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(updatesql);
            //getId nereikia, nes kuriant nauja irasa serveryje, id priskiriamas automatiskai
            preparedStatement.setString(1, computer.getName()); //cia jau is web prisiskyrem prie konstruktoriaus kintamojo ir sukurem user
            //objekta. Cia mes is objekta isiimam atitinkama duomeni ir paruosiam siusti i serveri
            preparedStatement.setString(2, computer.getType());
            preparedStatement.setString(3, computer.getInfo());
            preparedStatement.setInt(4, computer.getSpeed());
            preparedStatement.setInt(5, computer.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        }
        catch (SQLException e){
            System.out.println("SQLException klaida " + e);
        }
    }

    public void deletestudent(int id) {

        String deletesql = "DELETE FROM computer WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(deletesql);
            preparedStatement.setInt(1, id);
            //kuri apssirasem else if(action.equalsIgnoreCase("delete")){ ...}

            //delete kaip ir insertai
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }
        catch (SQLException e){
            System.out.println("SQLException klaida " + e);
        }

    }

}
