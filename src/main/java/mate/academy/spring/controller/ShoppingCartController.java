package mate.academy.spring.controller;

import mate.academy.spring.mapper.DtoResponseMapper;
import mate.academy.spring.model.ShoppingCart;
import mate.academy.spring.model.dto.response.ShoppingCartResponseDto;
import mate.academy.spring.service.MovieSessionService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final MovieSessionService movieSessionService;
    private final DtoResponseMapper<ShoppingCartResponseDto, ShoppingCart>
            shoppingCartResponseMapper;

    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService,
                                  MovieSessionService movieSessionService,
                                  DtoResponseMapper<ShoppingCartResponseDto, ShoppingCart>
                                          shoppingCartResponseMapper) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.movieSessionService = movieSessionService;
        this.shoppingCartResponseMapper = shoppingCartResponseMapper;
    }

    @PutMapping("/movie-sessions")
    public ShoppingCartResponseDto update(@RequestParam Long userId,
                                                      @RequestParam Long movieSessionId) {
        shoppingCartService.addSession(movieSessionService.get(movieSessionId),
                userService.get(userId));
        return shoppingCartResponseMapper
                .toDto(shoppingCartService.getByUser(userService.get(userId)));
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto findShoppingCartByUserId(@RequestParam Long userId) {
        return shoppingCartResponseMapper
                .toDto(shoppingCartService.getByUser(userService.get(userId)));
    }
}