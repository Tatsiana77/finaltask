package by.edu.sarnatskaya.pharmacy.model.dao;

public class ColumnName {

    /* users table*/
    public static final String USER_ID = "users.id";
    public static final String NAME = "users.name";
    public static final String SURNAME = "users.surname";
    public static final String LOGIN = "users.login";
    public static final String PASSWORD = "users.password";
    public static final String EMAIL = "users.email";
    public static final String PHONE = "users.phone";

    /* status table */
    public static final String USER_STATUS = "users_status.status";

    /*roles table */
    public static final String USER_ROLE = "roles.roles";

    /*preparations table */
    public static final String PREPARATIONS_ID = "preparations.id";
    public static final String PREPARATIONS_TITLE = "preparations.title";
    public static final String PREPARATIONS_PRICE = "preparations.price";
    public static final String PREPARATIONS_AMOUNT = "preparations.amount";
    public static final String PREPARATIONS_DESCRIPTION = "preparations.description";
    public static final String PREPARATIONS_IMAGE = "preparations.image";
    public static final String PREPARATIONS_ACTIVE = "preparations.active";

    /*categories*/
    public static final String CATEGORIES_ID = "categories.id";
    public static final String CATEGORIES_TYPE = "categories.type ";

    /*conditions*/
    public static final String CONDITIONS_ID = "conditions.id";
    public static final String CONDITIONS_STATUS = "conditions.condition_status";

    /* orders*/
    public static final String ORDERS_ID = "orders.id";
    public static final String ORDERS_CREATED = "orders.created";
    public static final String ORDERS_AMOUNT = "orders.amount";
    public static final String ORDERS_DOSE = "orders.dose";
    public static final String ORDERS_COST = "orders.cost";
    public static final String ORDERS_USER_ID = "orders.user_id";
    public static final String ORDERS_ORDERS_STATUS_ID = "orders.status_id";

    /*orders_status*/
    public static final String ORDERS_STATUS_ID = "orders_status.id";
    public static final String ORDERS_STATUS = "orders_status.status";

    /*purchases*/
    public static final String PURCHASES_ID = "purchases.id";
    public static final String ORDERS_QUANTITY = "purchases.quantity";

    /*prescriptions*/
    public static final String PRESCRIPTION_ID = " prescriptions.id";
    public static final String PRESCRIPTION_DOCTOR_ID = " prescriptions.doctor_id";
    public static final String PRESCRIPTION_DATE = " prescriptions.date";
    public static final String PRESCRIPTION_TIME = " prescriptions.time";
    public static final String PRESCRIPTION_ACTIVE = " prescriptions.is_active";
    public static final String PRESCRIPTION_COMMENT = " prescriptions.comment";
    public static final String PRESCRIPTION_PREPARATION_ID = " prescriptions.preparations_id";


    public ColumnName() {
    }
}
