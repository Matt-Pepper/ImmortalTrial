package com.immortaltrial.game.users;

public interface IUserService {
    User registerNewUserAccount(UserDto userDto);

    // Optional<User> getUser(String verificationToken);

    // void saveRegisteredUser(User user);

    // void deleteUser(User user);

    // void createVerificationTokenForUser(User user, String token);

    // Optional<VerificationToken> getVerificationToken(String verificationToken);

    // VerificationToken generateNewVerificationToken(String token);

    // void createPasswordResetTokenForUser(User user, String token);

    // Optional<User> findUserByEmail(String email);

    // PasswordResetToken getPasswordResetToken(String token);

    // Optional<User> getUserByPasswordResetToken(String token);

    // Optional<User> getUserByID(long id);

    // void changeUserPassword(User user, String password);

    // boolean checkIfValidOldPassword(User user, String password);

    // String validateVerificationToken(String token);

    // List<String> getUsersFromSessionRegistry();

    boolean emailExists(String email);

    boolean usernameExists(String username);
}
