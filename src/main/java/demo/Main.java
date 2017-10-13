package demo;

import com.google.gson.Gson;
import com.sun.org.apache.regexp.internal.RE;

import java.io.*;
import java.util.*;

import static spark.Spark.*;

// reikia, kad palaikytu lamda8 (file, structure>pakeist i JAVA 8)
public class Main {

    public static void main(String[] args) {
        // ateina uzklausa is tinklo, klausosi porto (klauso requesto ir siuncia response)
        // 8010 port'a galime bet koki pasirinkt
        port(8010);

        // Lambda8 (JAVA8)
        get("/pirmas", (request, response) ->
        "Pirmas veikia");


        // veiks kolkas per "Postman", nes cia post, bodyje gali pasirasyti, ka nori perduoti
        post("/antras", (request, response) -> "veikia2" + request.body());


        // get xml file (per postman veiks xml), o pe web password struglina, kazko neatpazista
        get("/getxml", (request, response) -> {
            // reikia nusakyti, koks atsakymas eis
            response.type("text/xml");
                return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<user>Janonis</user>\n" +
                "<password>password</password>";
        });

        // per nuoroda galime perduoti parametra
        // : reiskia, kad bus nuskaitomas parametras
        // narsykleje veiks> http://localhost:8010/username/mano vardas/passwords/mano passowrd
        get("/username/:name/passwords/:passw", (request, response) -> {
            String textReceived =  "labas " + request.params("name") + " passowrd is: " + request.params("passw");
            return textReceived;
                }
        );

        // dar su parametrasis per klaustuka
        // veiks taip > http://localhost:8010/users2?user=userInserted&passowrd=passordInserted
        get("/users2", (request, response) -> {
            String username = request.queryParams("user");
            String password = request.queryParams("passowrd");

            return "username is: " + username + " password is: " + password;
        });


        // gaunam is pirmo serverio i antra, pagraziname is antro serverio objekta i pirma
        get("/getJsonFromMicroservice", "application/json",(request, response) -> {
            String username = request.queryParams("user");
            String password = request.queryParams("passowrd");
            ResponseData responseData = new ResponseData(username, password);
            ResponseDATA1 responseDATA1 = new ResponseDATA1("info");
            ResponseDTO responseDTO = new ResponseDTO(responseData, responseDATA1, "name");
            response.header("Access-Control-Allow-Origin", "*");
            // pasikuriam Gson objekta
            Gson gson = new Gson();
            // sio reikia, nes kitu atbveju pagrazins i ajax text'a, nebent mes ajax'e jSon.parse() (ar pan)
            response.type("application/json");
            return gson.toJson(responseDTO);
        });
//        1. Pirmam serveri per AJAX URL turi buti: "http://localhost:8010/getJsonFromMicroservice?user=userInserted&passowrd=passordInserted" pasisetinam user ir password
//            Gali buti ir per Json data siunciamas
//        2. Atsisiuncia i kita serveri (musu mikroservisa kitame serveryje) mikroservice ir surenkam duomenis
//        3. Susirenkam mikroservise per requesta username ir password is musu AJAX (pirmo serverio)
//            Padarem modifikacija, kad patys pasikurem DTO (Data Transfer Object), kuris apima musu kitus modelius ir simple data bei pagrazinam ji
//        4. susikurus modelis ResponseData (modifikuotas responseDTO) sudedam musu gautus duomenis i objekta
//        5. Response (atsakymas i pirmaji serveri turi eiti per header "Access-Control-Allow-Origin", del saugumo
//             kitais atvejais gal nereikia, bet siuo  reikia)
//        6. pasikuriam Gson (i dependencies isirasom Gson (google json) parsesi)
//        7. returninam atsakyma Json formatu tiem, kas nores (uzklausime per XAMPP atsakymo ir per AJAX uzklause ir gave atsakyma, ji atvaizduojame musu puslapyje)



        // UZDUOTIS SU GET, PUT, POST, DELETE (CRUD):


        // GETS OBJECT FROM FILE BY ID
        get("/getTask/:id", (request, response) -> {

            Map<Integer, Computer55> map = new HashMap<>();

            File fileDir = new File("C:\\Users\\Code Academy\\IdeaProjects\\spark2\\src\\main\\java\\demo\\file.txt");
            BufferedReader in = null;
            // read file
            in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));
            // gera lines in one
            String line = "";
            while ((line=in.readLine()) != null) {
                // map will consist of 5 variables
                List<String> arrOfLine = new ArrayList<>(5);
                String[] splitLine = line.split("\\W+");
                // for each second variable we put in new list (because id=1; id >0 and 1 is 1'st element
                for(int i = 0; i < splitLine.length; i++){
                    if(i%2 != 0 && i != 0){
                        arrOfLine.add(splitLine[i]);
                    }
                }
                // put in map from array list
                map.put(Integer.parseInt(arrOfLine.get(0)), new Computer55(Integer.parseInt(arrOfLine.get(0)), arrOfLine.get(1), arrOfLine.get(2), arrOfLine.get(3), Integer.parseInt(arrOfLine.get(4))));
            }

            in.close();
            // params, kai per ":" eina, o queryParams, kai pas "?" eina
            int id = Integer.parseInt(request.params("id"));
            return map.get(id);
        });

        // ADDS OBJECT TO FILE
        post("/postTask", (request, response) -> {

            // random int
            Random rand = new Random();
            int  n = rand.nextInt(1000000000) + 1;

            Computer55 computer = new Computer55( n ,"test","test","test",1);
            try
            {
                String filename= "C:\\Users\\Code Academy\\IdeaProjects\\spark2\\src\\main\\java\\demo\\file.txt";
                FileWriter fw = new FileWriter(filename,true); //the true will append the new data
                fw.write(computer.toString()+"\n");//appends the string to the file
                fw.close();
            }
            catch(IOException ioe)
            {
                System.err.println("IOException: " + ioe.getMessage());
            }
            return computer;
        });

        // DELETE LINE/Object FROM FILE BY ID  (NEBAIGTAS IR SU KLAIDOM !!!!!)
        delete("/deleteTask/:id", (request, response) -> {
            List<Computer55> list = new ArrayList<>();
            File fileDir = new File("C:\\Users\\Code Academy\\IdeaProjects\\spark2\\src\\main\\java\\demo\\file.txt");
            BufferedReader in = null;
            // read file
            in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));
            String line = "";
            while ((line=in.readLine()) != null) {
                List<String> arrOfLine = new ArrayList<>(5);
                String[] splitLine = line.split("\\W+");
                // for each second variable we put in new list (because id=1; id >0 and 1 is 1'st element
                for(int i = 0; i < splitLine.length; i++){
                    if(i%2 != 0 && i != 0){
                        arrOfLine.add(splitLine[i]);
                    }
                }
                String passedLine = line;
//                // jei eilutej nera musu id, tai dedam i nauja lista objektu, jog paskui si paduotumem irasymui i faila
                if(!passedLine.toLowerCase().contains("id="+request.params("id").toLowerCase())){
                    // put in map from array list
                    list.add(new Computer55(Integer.parseInt(arrOfLine.get(0)), arrOfLine.get(1), arrOfLine.get(2), arrOfLine.get(3), Integer.parseInt(arrOfLine.get(4))));
                }
            }
            // irasau objektu lista paeiliui i faila
            String filename= "C:\\Users\\Code Academy\\IdeaProjects\\spark2\\src\\main\\java\\demo\\file.txt";
            FileWriter fw = new FileWriter(filename); // will replace the new data
            for(Computer55 insObj: list) {
                fw.write(insObj.toString()+"\n");//the string to the file
            }
            fw.close();
            return list.toString();
        });

        // UPDATE Objekta pagal ID (NEVEIKIA KOLKAS):
        // reiks kreiptis per > http://localhost:8010/updateTask?id=ID&name=NAME&osname=OSNAME&color=COLOR&ramsize=RAMSIZE
        put("/updateTask", (request, response) -> {
            Computer55 computer = new Computer55();
            List<Computer55> list = new ArrayList<>();

                // gaunam duomenis
                int id = Integer.parseInt(request.queryParams("id"));
                String name = request.queryParams("name");
                String osname = request.queryParams("osname");
                String color = request.queryParams("color");
                int ramsize = Integer.parseInt(request.queryParams("ramsize"));

            File fileDir = new File("C:\\Users\\Code Academy\\IdeaProjects\\spark2\\src\\main\\java\\demo\\file.txt");
            BufferedReader in = null;
            // read file
            in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));
            String line = "";
            while ((line=in.readLine()) != null) {
                String passedLine = line;
                // jei eilute turi musu id, tai keiciame requestintais duomenis
                if(passedLine.toLowerCase().contains("id="+id)){
                    computer.setId(id);
                    computer.setName(name);
                    computer.setOsname(osname);
                    computer.setColor(color);
                    computer.setRamsize(ramsize);
                    list. add(computer);
                }else {
                    // listas nauju duomenu
                    List<String> arrOfLine = new ArrayList<>(5);
                    String[] splitLine = line.split("\\W+");
                    // for each second variable we put in new list (because id=1; id >0 and 1 is 1'st element
                    for (int i = 0; i < splitLine.length; i++) {
                        if (i % 2 != 0 && i != 0) {
                            arrOfLine.add(splitLine[i]);
                        }
                    }
                    // put in map from array list
                    list.add(new Computer55(Integer.parseInt(arrOfLine.get(0)), arrOfLine.get(1), arrOfLine.get(2), arrOfLine.get(3), Integer.parseInt(arrOfLine.get(4))));
                }
            }
            // irasau objektu lista paeiliui i faila
            String filename= "C:\\Users\\Code Academy\\IdeaProjects\\spark2\\src\\main\\java\\demo\\file.txt";
            FileWriter fw = new FileWriter(filename); // will replace the new data
            for(Computer55 insObj: list) {
                fw.write(insObj.toString()+"\n");//the string to the file
            }
            fw.close();
            return list.toString();
        });

    }

}
