package demo;

import com.google.gson.Gson;
import dao.ComputerDao;

import java.util.List;

import static spark.Spark.*;

public class MainComputer {

    public static void main(String[] args) {

        port(8067);
        ComputerDao computerDao = new ComputerDao();

        // GET COMPUTER LIST
        // PVZ per Postman.: http://localhost:8067/getComputerListFromDB
        get("/getComputerListFromDB", "application/json",(request, response) -> {
            List<Computer> allComputer = computerDao.getAllComputer();
            response.header("Access-Control-Allow-Origin", "*");
            // pasikuriam Gson objekta
            Gson gson = new Gson();
            // sio reikia, nes kitu atbveju pagrazins i ajax text'a, nebent mes ajax'e jSon.parse() (ar pan)
            response.type("application/json");
            return gson.toJson(allComputer);
        });


        // ADD COMPUTER TO DB
        // PVZ per Postman.: http://localhost:8067/postComputerToDB?name=NAME&type=TYPE&info=INFO&speed=1
        post("/postComputerToDB", "application/json",(request, response) -> {
            String name = request.queryParams("name");
            String type = request.queryParams("type");
            String info = request.queryParams("info");
            int speed = Integer.parseInt(request.queryParams("speed"));
            Computer computer = new Computer(name, type, info, speed);
            computerDao.addComputer(computer);
            response.header("Access-Control-Allow-Origin", "*");
            // pasikuriam Gson objekta
            Gson gson = new Gson();
            // sio reikia, nes kitu atbveju pagrazins i ajax text'a, nebent mes ajax'e jSon.parse() (ar pan)
            response.type("application/json");
            return gson.toJson(computer);
        });


        // UPDATE COMPUTER TO DB
        // PVZ per Postman.: http://localhost:8067/putComputerToDB?id=1&name=NAME&type=TYPE&info=INFO&speed=1
        put("/putComputerToDB", "application/json",(request, response) -> {
            int id = Integer.parseInt(request.queryParams("id"));
            String name = request.queryParams("name");
            String type = request.queryParams("type");
            String info = request.queryParams("info");
            int speed = Integer.parseInt(request.queryParams("speed"));
            Computer computer = new Computer(id, name, type, info, speed);
            computerDao.updatestudent(computer);
            response.header("Access-Control-Allow-Origin", "*");
            // pasikuriam Gson objekta
            Gson gson = new Gson();
            // sio reikia, nes kitu atbveju pagrazins i ajax text'a, nebent mes ajax'e jSon.parse() (ar pan)
            response.type("application/json");
            return gson.toJson(computer);
        });


        // DELETE COMPUTER FROM DB
        // PVZ per Postman.: http://localhost:8067/deleteComputerByIdFromDB?id=1
        delete("/deleteComputerByIdFromDB", "application/json",(request, response) -> {
            int id = Integer.parseInt(request.queryParams("id"));
            computerDao.deletestudent(id);
            Computer computerById = computerDao.getComputerById(id);
            response.header("Access-Control-Allow-Origin", "*");
            // pasikuriam Gson objekta
            Gson gson = new Gson();
            // sio reikia, nes kitu atbveju pagrazins i ajax text'a, nebent mes ajax'e jSon.parse() (ar pan)
            response.type("application/json");
            return gson.toJson(computerById);
        });


    }

}
