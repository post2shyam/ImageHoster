package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Call the registerUser() method in the UserRepository class to persist the user record in the database
    public boolean registerUser(User newUser) {
        //Check for password strength before registering the user successfully
        if (isWeakPassword(newUser.getPassword())) {
           return false;
        }
        userRepository.registerUser(newUser);
        return true;
    }

    //Since we did not have any user in the database, therefore the user with username 'upgrad' and password 'password' was hard-coded
    //This method returned true if the username was 'upgrad' and password is 'password'
    //But now let us change the implementation of this method
    //This method receives the User type object
    //Calls the checkUser() method in the Repository passing the username and password which checks the username and password in the database
    //The Repository returns User type object if user with entered username and password exists in the database
    //Else returns null
    public User login(User user) {
        User existingUser = userRepository.checkUser(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            return existingUser;
        } else {
            return null;
        }
    }

    private boolean isWeakPassword(String password) {
        return calculatePasswordStrength(password) < 3;
    }

    private int calculatePasswordStrength(String password) {
        //total score of password
        int iPasswordScore = 0;

        if (password.length() >= 3) {
            //if it contains one digit, add 1 to total score
            if (password.matches("(?=.*[0-9]).*"))
                iPasswordScore += 1;

            //if it contains one letter, add 1 to total score
            if (password.matches("(?=.*[A-Za-z]).*"))
                iPasswordScore += 1;

            //if it contains one special character, add 1 to total score
            if (password.matches("(?=.*[~!@#$%^&*()_-]).*"))
                iPasswordScore += 1;
        }
        return iPasswordScore;
    }
}
