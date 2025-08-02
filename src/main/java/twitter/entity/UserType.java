package twitter.entity;

import twitter.exception.UnknowUserTypeException;

public enum UserType {
    PERSON(0),
    ORGANIZATION(1);

    private final Integer value;

    UserType(Integer value) {
        this.value = value;
    }

    public static UserType getValue(Integer value) throws UnknowUserTypeException {
        for (UserType userType : UserType.values()) {
            if (userType.value.equals(value)) {
                return userType;
            }
        }
        throw  new UnknowUserTypeException("не найден тип пользователя по значении " + value);
    }
}
