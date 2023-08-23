import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Menu();
    }

    public static void Menu() {
        Scanner in = new Scanner(System.in);

        System.out.println("Please select an option out of the 4: " +
                "\n 1. Modify Film-List " +
                "\n 2. Create a new Film-List " +
                "\n 3. Delete Film-List " +
                "\n 4. Quit");
        String userOption = in.nextLine();

        switch (userOption){
            case ("1"):
                ModifyFilmList();
            case ("2"):
                CreateFilmList();
            case ("3"):
                DeleteFilmWatchList();
            case ("4"):
                Quit();
        }
    }


    //Modify Film-List
    public static void ModifyFilmList() {
        Scanner in = new Scanner(System.in);

        File selectedFile = new File(FileExplorer());

        //Ask and Present options
        System.out.println("File selected: "+selectedFile.getName()+" \nPlease select an option out of the 4: " +
                "\n 1. Add Films to Film-List " +
                "\n 2. Remove Films from Film-List " +
                "\n 3. View Film-List " +
                "\n 4. Return to main menu");
        String userOption = in.nextLine();

        switch (userOption) {
            case("1"):
                AddFilms(selectedFile);
            case("2"):
                RemoveFilms(selectedFile);
            case("3"):
                ViewFilmList(selectedFile);
            case("4"):
                ReturnToMainMenu();
        }
    }

    public static void ModifyFilmListContinuity(File selectedFile) {
        Scanner in = new Scanner(System.in);

        System.out.println("File selected: "+selectedFile.getName()+" \nPlease select an option out of the 4: " +
                "\n 1. Add Films to Film-List " +
                "\n 2. Remove Films from Film-List " +
                "\n 3. View Film-List " +
                "\n 4. Return to main menu");
        String userOption = in.nextLine();

        switch (userOption) {
            case("1"):
                AddFilms(selectedFile);
            case("2"):
                RemoveFilms(selectedFile);
            case("3"):
                ViewFilmList(selectedFile);
            case("4"):
                ReturnToMainMenu();
        }
    }


    public static void AddFilms(File selectedFile) {
        //Ask User to enter the film-name/director
        //Initialise the film object
        //Append to the end of the selected file
        //Exit

        AddReadFilmsToAList(selectedFile); // Add all films from file to an arrayList
        //Film newFilm = CreatingNewFilmObj(); //Create a new obj with user data

        //Overwrite file with the contents of ArrayList (with the new obj)
        ModifyFilmListContinuity(selectedFile);
    }

    public static Film CreatingNewFilmObj() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please Enter the name of the film you want to add: ");
        String filmName = in.nextLine();

        System.out.println("Please Enter the director of the film you want to add: ");
        String filmDirector = in.nextLine();

        Film filmNew = new Film(filmName, filmDirector);

        return filmNew;
    }

    //Change to Return ArrayList
    public static void AddReadFilmsToAList(File selectedFile) {
        ArrayList<Film> filmWatchListAList = new ArrayList<Film>(); //ArrayList to be added to
        createFileArrayList(selectedFile, filmWatchListAList); //Creates ArrayList with File Data

        //Ask User for data to create new film
        Film newFilm = CreatingNewFilmObj();

        //Add to ArrayList (With files)
        filmWatchListAList.add(newFilm);

        //Write all ArrayList back to the selected file
        writeArrayListToFile(selectedFile, filmWatchListAList);


    }

    public static void RemoveFilms(File selectedFile) {
        ArrayList<Film> filmArrayList = new ArrayList<Film>();

        //Read Selected File into an ArrayList
        createFileArrayList(selectedFile, filmArrayList);

        //Output ArrayList
        readDataFromFile(selectedFile);

        //Ask the user which elements of the ArrayList to delete
        System.out.println("Which film do you want to delete from the above Film-watchlist (select the corresponding value to line value)? ");
        Scanner in = new Scanner(System.in);
        int selectedElement = in.nextInt();
        selectedElement = selectedElement - 1;


        //Select the respective array obj
        for (int i = 0; i < filmArrayList.size(); i++) {
            try {
                filmArrayList.remove(selectedElement);
            } catch (Exception e) {
                System.out.println("Value was out of bounds or unable to be removed: Please try again.");
                ModifyFilmListContinuity(selectedFile);
            }
        }

        //Write the ArrayList to the selected file (over-write the entire file)
        writeArrayListToFile(selectedFile, filmArrayList);

        //Exit
        ModifyFilmListContinuity(selectedFile);
    }

    public static void ViewFilmList(File selectedFile) {
        readDataFromFile(selectedFile);
        System.out.println("Press E to exit view: ");
        Scanner in = new Scanner(System.in);
        String inputString = in.next();

        if (inputString.equalsIgnoreCase("e".toUpperCase())) {
            ModifyFilmListContinuity(selectedFile);
        } else {
            System.out.println("Invalid input: Please try again.");
        }
        ViewFilmList(selectedFile);
    }

    public static void readDataFromFile(File fileRead) {
        System.out.println("File Contents of the file: " + fileRead.getName() + ": " + "\n==================================================");

        ArrayList<Film> filmArrayList = new ArrayList<Film>();
        createFileArrayList(fileRead, filmArrayList);

        for (int i = 0; i < filmArrayList.size(); i++) {
            String filmName = filmArrayList.get(i).getFilmName();
            String filmDirector = filmArrayList.get(i).getFilmDirector();
            System.out.println("Value: " + i + " / Film Name: " + filmName + " / Film Director: " + filmDirector);
        }
    }

    public static ArrayList<Film> createFileArrayList(File fileRead, ArrayList<Film> filmArrayList) { //Add ArrayList here
        Scanner fileScanner = null;

        try {
            fileScanner = new Scanner(fileRead);
            while (fileScanner.hasNextLine()) {
                String fileContents = fileScanner.nextLine();
                Scanner fileContentsParse = new Scanner(fileContents);
                fileContentsParse.useDelimiter("/");
                String filmName = fileContentsParse.next();
                String filmDirector = fileContentsParse.next();
                Film filmObj = new Film(filmName, filmDirector);
                filmArrayList.add(filmObj); //Returns Films Read From The File
            }
            return filmArrayList;
        } catch (FileNotFoundException e) {
            System.out.println("File Does Not Exist/Not Found");
            System.exit(0);
        }
        return null;
    }

    public static void writeArrayListToFile(File selectedFile, ArrayList<Film> filmArrayList) {
        try {
            //Writing to selected file
            PrintWriter writer = new PrintWriter(selectedFile);

            for (int i = 0; i < filmArrayList.size(); i++) {
                writer.write(filmArrayList.get(i).getFilmName() + "/" + filmArrayList.get(i).getFilmDirector()+"\n");
            }

            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("File Does Not Exist/Not Found");
            System.exit(0);
        }
    }

    //Create a Film-List
    public static void CreateFilmList() {
        Scanner in = new Scanner(System.in);
        //Ask User for New File's List
        System.out.println("Please write a name for this new movie-watchlist: ");
        String newFileName = in.nextLine();

        //Create File
        try {
            File newFile = new File(newFileName+".txt");
            boolean result = newFile.createNewFile();
            if (result) {
                System.out.println("File: " + newFile.getName() + " has been created.");
                ReturnToMainMenu();
            } else {
                System.out.println("The file already exits, please try again");
                CreateFilmList();
            }
        } catch (IOException e) {
            System.out.println("A File Exception has occurred, please try again");
            CreateFilmList();
        }
    }

    //Delete a Film-List
    public static void DeleteFilmWatchList() {
        File selectedFile = new File(FileExplorer());
        //Delete File
        if (selectedFile.delete()) {
            System.out.println("Deleted the file: " + selectedFile.getName());
            ReturnToMainMenu();
        } else {
            System.out.println("Failed to delete the file- please try again.");
            DeleteFilmWatchList();
        }
    }


    //Obtain the File
    public static String FileExplorer() {
        System.out.println("PLEASE SELECT A FILE");
        String dir = System.getProperty("user.dir") + "\\src";

        File rootDir = new File(dir);

        //View FileExplorer
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getParentDirectory(rootDir));
        int returnVal = jfc.showOpenDialog(null);

        //Select a File
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            return selectedFile.getAbsolutePath();
        } else {
            System.out.println("File was not selected");
            ReturnToMainMenu();
        }
        return null;
    }

    //Quit
    public static void Quit() {
        System.exit(0);
    }

    //Return
    public static void ReturnToMainMenu() {
        Menu();
    }
}