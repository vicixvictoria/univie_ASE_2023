package com.ase.login.business;

import com.ase.login.dataAccess.JWTHelper;
import com.ase.login.dataAccess.JWTResponse;
import com.ase.login.domain.IUserData;
import com.ase.login.domain.MyUser;
import com.ase.login.exception.JWTInvalidException;
import com.ase.login.exception.PasswordIncorrectException;
import com.ase.login.exception.UserAlreadyExistsException;
import com.ase.login.exception.UserDetailsException;
import com.ase.login.exception.UserNotFoundException;
import com.ase.login.integration.Publisher;
import com.ase.login.dataAccess.IMyUserRepository;
import java.lang.invoke.MethodHandles;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Coordinates domain logic in the system. It is responsible for receiving calls from the network
 * layer and routing them to the specific domain components. Also holds the repositories and is
 * responsible for writing and reading from them.
 * <p>
 * Also, responsible for publishing a message when the underlying data has changed
 */
@Service
public class LoginService {
    private final static Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final IMyUserRepository myUserRepository;
    private final Publisher publisher;
    private final JWTHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(IMyUserRepository myUserRepository, Publisher publisher,
            JWTHelper jwtHelper, PasswordEncoder passwordEncoder) {
        this.myUserRepository = myUserRepository;
        this.publisher = publisher;
        this.jwtHelper = jwtHelper;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Adds a user into the system, with the given user data.
     *
     * @param newUserData the data of the added user
     * @throws UserAlreadyExistsException thrown if the email contained in the given user data
     *                                    already exists in the system.
     */
    public void addUser(IUserData newUserData) throws UserAlreadyExistsException {
        Optional<MyUser> queryResult = myUserRepository.findMyUserByEmail(newUserData.getEmail());
        if (queryResult.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        // TODO: MAYBE ADD SOME FACTORY FOR THIS?
        MyUser newUser = new MyUser(newUserData.getEmail(),
                passwordEncoder.encode(newUserData.getPassword()),
                newUserData.getRole());

        myUserRepository.save(newUser);
        publisher.newUserCreated(newUser);
    }

    /**
     * Returns whether the given JWT token is valid. (so whether it was created with this
     * applications secret and whether it has not expired)
     *
     * @param token the JWT token to be checked TODO: maybe wrap in custom class
     * @return true/false, whether the token is valid or not
     */
    public boolean verifyToken(String token) {
        return jwtHelper.verifyToken(
                token); // as this is just a mockup, always return that the token is valid
    }

    private MyUser findUserById(String userId) throws UserNotFoundException {
        Optional<MyUser> queryResult = myUserRepository.findById(userId);
        if (queryResult.isEmpty()) {
            throw new UserNotFoundException(
                    String.format("The request user with the id: %s was not found!", userId));
        }

        return queryResult.get();
    }

    /**
     * Updates the user with the given userId with the given user data.
     *
     * @param userId          the ID of the to be updated user
     * @param updatedUserData the updated information of the user. If not all fields of the user
     *                        data were update, ths should contain the old data.
     * @throws UserNotFoundException      thrown if the given userId was not found in the system
     * @throws UserAlreadyExistsException thrown if the user data contains an email which is already
     *                                    contained in the system.
     */
    public void updateUser(String userId, IUserData updatedUserData)
            throws UserNotFoundException, UserAlreadyExistsException {
        MyUser dbUser = findUserById(userId);

        if (!dbUser.getEmail().equals(updatedUserData.getEmail())) {
            // if the email changed, check if a user with such an email already exists
            Optional<MyUser> queryResult = myUserRepository
                    .findMyUserByEmail(updatedUserData.getEmail());
            if (queryResult.isPresent()) {
                throw new UserAlreadyExistsException(
                        "A user with the provided email already exists");
            }
        }

        dbUser.setEmail(updatedUserData.getEmail());
        dbUser.setPassword(passwordEncoder.encode(updatedUserData.getPassword()));

        publisher.userUpdated(dbUser);
    }

    /**
     * Returns the user with the given id.
     *
     * @param userId the userId of the searched for user
     * @return the searched for user
     * @throws UserNotFoundException thrown, if the userId does not exist in the system.
     */
    public MyUser getUser(String userId) throws UserNotFoundException {
        return findUserById(userId);
    }

    /**
     * Deletes the user with the given id from the system
     *
     * @param userId the id of the do be deleted user
     * @throws UserNotFoundException thrown, if the given userId does not exist in the system
     */
    public void deleteUser(String userId) throws UserNotFoundException {
        MyUser oldUser = findUserById(userId);
        myUserRepository.delete(oldUser);
        publisher.userDeleted(oldUser);
    }

    /**
     * Creates a JWT token given a users login information. This information is then verified and if
     * it is correct a JWT token is generated and returned.
     *
     * @param email    the login email
     * @param password the login password
     * @return a JWTResponse, which contains the JWT, the userId and the role of the user.
     * @throws UserDetailsException if the given email was not found in the system
     */
    public JWTResponse login(String email, String password) throws UserDetailsException {
        LOGGER.debug(String.format("Received login request for: %s;%s", email, password));

        Optional<MyUser> queryResult = myUserRepository.findMyUserByEmail(email);

        if (queryResult.isEmpty()) {
            throw new UserNotFoundException(
                    String.format("The user with the email %s was not found", email));
        }

        MyUser user = queryResult.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordIncorrectException(
                    "The given password did not match with the stored password!" + password + " " + user.getPassword());
        }

        return jwtHelper.generateJWTResponse(user);
    }

    public MyUser getUserByToken(String token) throws JWTInvalidException, UserNotFoundException {
        String userId = jwtHelper.extractId(token);
        Optional<MyUser> queryResult = myUserRepository.findById(userId);

        if (queryResult.isEmpty()) {
            throw new UserNotFoundException("Could not find the owner of the JWT");
        }
        return queryResult.get();
    }
}
