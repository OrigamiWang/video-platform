package szu.util;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import szu.model.User;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class AuthUtil {

    public static Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return Optional.empty();
        } else {
            return Optional.of((User) authentication.getPrincipal());
        }
    }

    public static void setCurrentUser(@NotNull String token, @NotNull User user) {
        SecurityContextHolder.getContext().setAuthentication(new RememberMeAuthenticationToken(token, user, null));
    }

}
