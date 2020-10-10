import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 Get sorted list of private fields the object declares (inherited fields should be skipped).
 */
class FieldGetter {

    public List<String> getPrivateFields(Object object) {
        Class c = object.getClass();
        Field[] fields = c.getDeclaredFields();
        List<String> privateFields = new ArrayList<>();
        for (Field field: fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isPrivate(modifiers)) {
                privateFields.add(field.getName());
            }
        }
        Collections.sort(privateFields);
        return privateFields;
    }

}