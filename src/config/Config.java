package config;

import controller.UserController;
import model.store.Store;
import model.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config<E> {
     private static UserController userController = new UserController();

    public static Scanner scanner() {
        Scanner scanner = new Scanner(System.in);
        return scanner;
    }


    public static final String PATH_USER = "C:\\Users\\Admin\\IdeaProjects\\MD3_TechnologyShop\\src\\database\\user.txt";
    public static final String PATH_USER_LOGIN = "C:\\Users\\Admin\\IdeaProjects\\MD3_TechnologyShop\\src\\database\\userLogin.txt";
    public static final String PATH_PRODUCT = "C:\\Users\\Admin\\IdeaProjects\\MD3_TechnologyShop\\src\\database\\product.txt";
    public static  final String PATH_CART = "C:\\Users\\Admin\\IdeaProjects\\MD3_TechnologyShop\\src\\database\\cart.txt";
    public static final String PATH_ORDER =  "C:\\Users\\Admin\\IdeaProjects\\MD3_TechnologyShop\\src\\database\\order.txt";
    public static  final String PATH_STORE = "C:\\Users\\Admin\\IdeaProjects\\MD3_TechnologyShop\\src\\database\\store.txt";
    public static final String PATH_NOTIFICATION = "C:\\Users\\Admin\\IdeaProjects\\MD3_TechnologyShop\\src\\database\\notification.txt";
    public static final String PATH_AUCTION = "C:\\Users\\Admin\\IdeaProjects\\MD3_TechnologyShop\\src\\database\\auction.txt";

    public List<E> readFromFile(String PATH_FILE) {
        List<E> eList = new ArrayList<>();
        File file = new File(PATH_FILE);
        if (!file.exists()) {
            System.out.println("File not found: " + PATH_FILE);
            return eList;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            eList = (List<E>) objectInputStream.readObject();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }

        return eList;
    }

    public void writeFromFile(String PATH_FILE, List<E> eList) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PATH_FILE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(eList);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (Exception e) {

        }
    }


    public void writeToFile(String PATH_FILE, E object) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH_FILE);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {

        }
    }

    public E readToFile(String PATH_FILE) {
        E object = null;
        try (FileInputStream fileInputStream = new FileInputStream(PATH_FILE);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            object = (E) objectInputStream.readObject();
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }
        return object;
    }

    public static final String validateEmail = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
    public static final String validatePhone = "^0(\\d){9}$";
    public static final String validateUserName = "^[a-z0-9._-]{6,15}$";


    public static boolean setValidateEmail(String email) {
        for (User user : userController.findAll()) {
            if (user.getEmail().equals( email)) {
                System.out.println("Email already exists");
                return false; // Email đã tồn tại, không hợp lệ
            }
        }

        Pattern pattern = Pattern.compile(validateEmail);
        Matcher matcher = pattern.matcher( email);
        if (!matcher.matches()) {
            System.out.println("Invalid email format");
            return false;
        }

        return true;
    }
    public static boolean setValidatePhone(String phone) {
        for (User user : userController.findAll()) {
            if (user.getPhoneNumber().equals( phone)) {
                System.out.println("Username already exists");
                return false;
            }
        }

        Pattern pattern = Pattern.compile(validatePhone);
        Matcher matcher = pattern.matcher( phone);
        if (!matcher.matches()) {
            System.out.println("Invalid phone number format.Phone number must have 10 digits and start with 0 ");
            return false;
        }

        return true;
    }
    public static boolean setValidateUserName(String userName) {

        for (User user : userController.findAll()) {
            if (user.getUserName().equals( userName)) {
                System.out.println("Username already exists");
                return false; // Tên người dùng đã tồn tại, không hợp lệ
            }
        }

        Pattern pattern = Pattern.compile(validateUserName);
        Matcher matcher = pattern.matcher( userName);
        if (!matcher.matches()) {
            System.out.println("Invalid username format. Username must be more than 6 characters");
            return false; // Tên người dùng không khớp với mẫu, không hợp lệ
        }

        return true; // Tên người dùng hợp lệ
    }
    public static boolean setValidatePhoneNumber(String phone) {
        Pattern pattern = Pattern.compile(validatePhone);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }


}

