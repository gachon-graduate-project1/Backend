package homemate.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),  GUEST("ROLE_GUEST"), USER("ROLE_USER");

    private final String key;
}
