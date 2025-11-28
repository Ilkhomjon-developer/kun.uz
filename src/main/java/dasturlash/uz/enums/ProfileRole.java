package dasturlash.uz.enums;

import java.util.LinkedList;
import java.util.List;

public enum ProfileRole {

    ROLE_MODERATOR, ROLE_PUBLISHER, ROLE_USER, ROLE_ADMIN;

    public static List<ProfileRole> values(String[] roles) {
        if (roles == null || roles.length == 0) {
            return new LinkedList<>();
        }
        List<ProfileRole> roleList = new LinkedList<>();
        for (String role : roles) {
            roleList.add(ProfileRole.valueOf(role));
        }
        return roleList;
    }

    public static List<ProfileRole> valuesFromStr(String valuesStr) {
        if (valuesStr == null) {
            return new LinkedList<>();
        }
        return values(valuesStr.split(","));
    }
}
