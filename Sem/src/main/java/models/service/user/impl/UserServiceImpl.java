package models.service.user.impl;

import models.entities.Game;
import models.entities.User;
import models.repositories.user.UserRepository;
import models.repositories.user.impl.UserRepositoryImpl;
import models.service.game.GameService;
import models.service.game.impl.GameServiceImpl;
import models.service.user.UserService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserRepository userRepository=new UserRepositoryImpl();
    GameService gameService=new GameServiceImpl();

    public String signUp(String nick, String firstName, String lastName, String mail, String password,String img) {
        if(nick.equals("") || mail.equals("") || password.equals("")){
            return "Please fill in all required fields!";
        }
        List<String> checkColumnNick=userRepository.findAll("nick");
        List<String> checkColumnMail=userRepository.findAll("mail");
        if(checkColumnMail.contains(mail)){
            return "This email is already in use";
        }
        if(checkColumnNick.contains(nick)){
            return "This nick is already in use";
        }
        String salt;
        try {
            salt=getSalt();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        User user=User.builder()
                .nick(nick)
                .firstName(firstName)
                .lastName(lastName)
                .mail(mail)
                .password(get_SHA_512_SecurePassword(password,salt))
                .salt(salt)
                .build();
        Long id=userRepository.save(user);
        if(img!=""){
            try{
                BufferedInputStream inputStream=new BufferedInputStream(new URL(img).openStream());
                new File("..\\fileForImages\\UsersAvatar").mkdirs();
                File file=new File("..\\fileForImages\\UsersAvatar\\"+id+".png");
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e){
                try{
                    new File("..\\fileForImages\\UsersAvatar").mkdirs();
                    File file1=new File("..\\fileForImages\\UsersAvatar\\def.png");
                    File file2=new File("..\\fileForImages\\UsersAvatar\\"+id+".png");
                    Files.copy(file1.toPath(),file2.toPath());
                }catch (IOException e1){
                    throw new RuntimeException(e1);
                }
            }
        }else{
            try{
                new File("..\\fileForImages\\UsersAvatar").mkdirs();
                File file1=new File("..\\fileForImages\\UsersAvatar\\def.png");
                File file2=new File("..\\fileForImages\\UsersAvatar\\"+id+".png");
                Files.copy(file1.toPath(),file2.toPath());
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        return "All clear!";
    }


    public void downloadDefaultAvatar(){
        try{
            BufferedInputStream inputStream=new BufferedInputStream(new URL("https://w7.pngwing.com/pngs/562/56/png-transparent-playstation-drawing-game-controllers-line-art-video-game-gamepad-game-angle-white.png").openStream());
            new File("..\\fileForImages\\UsersAvatar").mkdirs();
            File file=new File("..\\fileForImages\\UsersAvatar\\def.png");
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }



    public User findUser(String mail,String password){
        User user1 = userRepository.findByUniq(mail);
        if(user1!=null && user1.getPassword().equals(get_SHA_512_SecurePassword(password,user1.getSalt()))){
            return user1;
        }
        return null;
    }

    public String update(Long id,String nick, String firstName, String lastName, String mail, String password,boolean admin,String salt) {
        if(nick.equals("") || mail.equals("") || password.equals("")){
            return "Please fill in all required fields!";
        }
        List<User> checkColumn=userRepository.findAllUsers("id","asc",null,null);
        for(long i=0;i<checkColumn.size();i++){
            if(checkColumn.get((int)i).getId()!=id && checkColumn.get((int)i).getMail().equals(mail)){
                return "This email is already in use";
            }
        }

        for(long i=0;i<checkColumn.size();i++){
            if(checkColumn.get((int)i).getId()!=id && checkColumn.get((int)i).getNick().equals(nick)){
                return "This nick is already in use";
            }
        }


        User user=User.builder()
                .id(id)
                .nick(nick)
                .firstName(firstName)
                .lastName(lastName)
                .mail(mail)
                .password(get_SHA_512_SecurePassword(password,salt))
                .isAdmin(admin)
                .salt(salt)
                .build();
        userRepository.update(user);
        return "All clear!";
    }


    public void signAsAdmin(Long id) {
        userRepository.signAsAdmin(id);
    }

    public void signAsUser(Long id) {
        userRepository.signAsUser(id);
    }


    public List<User> findAllUsers(String column,String order,String columnForFilter,String argumentOfFilter){
        return userRepository.findAllUsers(column,order,columnForFilter,argumentOfFilter);
    }


    public void deleteById(Long idOfUser){
        userRepository.delete(idOfUser);
    }

    public List<Game> getAvailableGames(Long id_of_user){
        List<Long> idOfAvailableGames=userRepository.getIdOfAvailableGames(id_of_user);
        List<Game> availableGames=new LinkedList<>();
        for(Long i: idOfAvailableGames){
            availableGames.add(gameService.findById(i));
        }
        return availableGames;
    }



    public String get_SHA_512_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public String getSalt() throws NoSuchAlgorithmException {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt.toString();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }

    }
}
