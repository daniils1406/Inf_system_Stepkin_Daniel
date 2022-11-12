package models.service.user;

import models.entities.Game;
import models.entities.User;

import java.util.List;

public interface UserService {
    public String signUp(String nick, String firstName, String lastName, String mail, String password,String img);
    public void downloadDefaultAvatar();
    public User findUser(String mail, String password);
    public String update(Long id,String nick, String firstName, String lastName, String mail, String password,boolean admin,String salt);
    public void signAsAdmin(Long id);
    public void signAsUser(Long id);
    public List<User> findAllUsers(String column,String order,String columnForFilter,String argumentOfFilter);
    public void deleteById(Long idOfUser);
    public List<Game> getAvailableGames(Long id_of_user);
    public String get_SHA_512_SecurePassword(String passwordToHash, String salt);

}
