package mate.academy.service.impl;

import mate.academy.dao.ShoppingCartDao;
import mate.academy.dao.TicketDao;
import mate.academy.dao.impl.ShoppingCartImpl;
import mate.academy.dao.impl.TicketDaoImpl;
import mate.academy.exception.RegistrationException;
import mate.academy.model.MovieSession;
import mate.academy.model.ShoppingCart;
import mate.academy.model.Ticket;
import mate.academy.model.User;
import mate.academy.service.ShoppingCartService;

import java.util.Optional;

public class ShoppingCartServiceImpl implements ShoppingCartService {
    private TicketDao ticketDao = new TicketDaoImpl();
    private ShoppingCartDao shoppingCartDao = new ShoppingCartImpl();

    @Override
    public void addSession(MovieSession movieSession, User user) throws RegistrationException {
        if (getByUser(user) == null) {
            throw new RegistrationException("Shopping cart by"
                    + " the user is not registered");
        }
        Ticket ticket = new Ticket();
        ticket.setMovieSession(movieSession);
        ticket.setUser(user);
        ticketDao.add(ticket);
        ShoppingCart byUser = getByUser(user);
        byUser.getTickets().add(ticket);
        shoppingCartDao.update(byUser);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        Optional<ShoppingCart> byUser = shoppingCartDao.getByUser(user);
        return byUser.orElse(null);
    }

    @Override
    public void registerNewShoppingCart(User user) throws RegistrationException {
        if (getByUser(user) != null) {
            throw new RegistrationException("The shopping cart" +
                    "by this user is already registered");
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartDao.add(shoppingCart);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.setUser(null);
        shoppingCart.setTickets(null);
        shoppingCart.setId(null);
        shoppingCartDao.update(shoppingCart);
    }
}
