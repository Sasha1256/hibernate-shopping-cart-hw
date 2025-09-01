package mate.academy.service;

import mate.academy.exception.RegistrationException;
import mate.academy.model.MovieSession;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;

public interface ShoppingCartService {

    void addSession(MovieSession movieSession, User user) throws RegistrationException;

    ShoppingCart getByUser(User user);

    void registerNewShoppingCart(User user) throws RegistrationException;

    void clear(ShoppingCart shoppingCart);
}
