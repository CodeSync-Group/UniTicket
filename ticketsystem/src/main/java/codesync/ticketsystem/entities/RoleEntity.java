package codesync.ticketsystem.entities;

public enum RoleEntity {
    ADMIN,
    HEADUNIT,
    HEADSHIP,
    ANALYST,
    HIGHPRIO,
    MIDPRIO,
    STUDENT,
    EXTERNAL;

    public static RoleEntity fromString(String roleStr) throws Exception {
        for (RoleEntity role : RoleEntity.values()) {
            if (role.name().equalsIgnoreCase(roleStr)) {
                return role;
            }
        }
        throw new Exception("Role " + roleStr + " not found.");
    }
}
