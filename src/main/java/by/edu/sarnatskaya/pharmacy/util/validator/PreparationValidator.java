package by.edu.sarnatskaya.pharmacy.util.validator;

import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreparationValidator {

    private static final String TITLE_REGEX = "[A-Za-zА-Яа-я-, ]{2,75}";
    private static final String PRICE_REGEX = "\\d{1,5}|\\d{1,5}\\.\\d{1,2}";
    private static final String AMOUNT_REGEX = "\\d{1,5}|\\d{1,5}\\.\\d{1,2}";
    private static final String DESCRIPTION_REGEX = ".{10,500}";
    private static final String INCORRECT_SYMBOL = "[\\p{Punct}&&[^-]]|-{2,}| {2,}|^[- ]|[- ]$";
    private static final Pattern pattern = Pattern.compile(INCORRECT_SYMBOL);
    private static final String PREPARATION_TYPE_REGEX = "[A-Za-zА-Яа-я-,.!?\\\"\\\"%()\\\\s]{3,45}";
    private static final String NUMBER_REGEX = "\\d+";
    private static final String AVAILABLE_TYPE_OF_PREPARATION = "Вегетотропные средства| Гематотропные средства |" +
            "Гомеопатические средства |Гормоны и их антагонисты|" +
            "Иммунотропные средства |Нейротропные средства |" +
            "Ненаркотические анальгетики |" +
            " Противомикробные, противопаразитарные и противоглистные средства| " +
            "Противоопухолевые средства | Регенеранты и репаранты| Разные вещества";

    public PreparationValidator() {
    }

    public static boolean isPreparationParamsValid(Map<String, String> checkDataResult, InputStream image) {
        boolean result = checkDataResult != null;
        if (result) {
            String title = checkDataResult.get(TITLE_REGEX);
            String price = checkDataResult.get(PRICE_REGEX);
            String amount = checkDataResult.get(AMOUNT_REGEX);
            String description = checkDataResult.get(DESCRIPTION_REGEX);
            String type = checkDataResult.get(PREPARATION_TYPE_REGEX);
            result = title != null && price != null && amount != null && description != null && image != null;
            if (result) {
                Matcher titleMatcher = pattern.matcher(title);
                Matcher typeMatcher = pattern.matcher(type);
                result = !title.isBlank() && title.matches(TITLE_REGEX) && titleMatcher.find() &&
                        type.matches(AVAILABLE_TYPE_OF_PREPARATION)
                        && !type.isBlank() && type.matches(PREPARATION_TYPE_REGEX)
                        && typeMatcher.find()
                        && !amount.isBlank() && amount.matches(AMOUNT_REGEX)
                        && !price.isBlank() && price.matches(PRICE_REGEX);
            }
        }
        return result;
    }

    public static boolean isPreparationTypeValid(String preparationType) {
        return preparationType != null && preparationType.matches(AVAILABLE_TYPE_OF_PREPARATION);
    }

    public static boolean isPreparationOrderExist(String[] preparationIdArr, String[] preparationNumArr) {
        boolean result = preparationIdArr != null && preparationNumArr != null && preparationIdArr.length == preparationNumArr.length;
        if (result) {
            int counter = 0;
            while (counter < preparationIdArr.length) {
                result = preparationIdArr[counter].matches(NUMBER_REGEX) && preparationNumArr[counter].matches(NUMBER_REGEX);
                if (!result) {
                    break;
                }
                counter++;
            }
        }
        return result;
    }
}


