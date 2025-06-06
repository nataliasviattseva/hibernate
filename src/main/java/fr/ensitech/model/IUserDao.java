package fr.ensitech.model;

import fr.ensitech.entity.User;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IUserDao {

  User createUser(User user) throws Exception;
  User getUserById(Long id) throws Exception;
  void updateUser(User user) throws Exception;
  void deleteUser(User user) throws Exception;
  List<User> getAllUsers() throws Exception;
  List<User> getUsersByNameAndLastName(String name, String lastName) throws Exception;
  Set<User> getUsersOfCity(String city) throws Exception;
  Map<String, List<User>> getUsersByCity(String city) throws Exception;
}
