package by.edu.sarnatskaya.pharmacy.model.entity;

import java.util.Objects;

public class Category extends AbstractEntity {

    private String categoryType;

    public Category(int categoryId, String categoryType) {
    }

    public Category(String type) {
        this.categoryType = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;//метод объекта getClass() возвращает экземпляр класса Class,
        // который содержит информацию о классе, из которого был вызван getClass ()
        if (!super.equals(obj))
            return false;
        Category category = (Category) obj;
        if (categoryType != null ? !categoryType.equals(category.categoryType) : category.categoryType != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (categoryType != null ? categoryType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Category [")
                .append(", categoryType=").append(categoryType)
                .append(" ]").toString();
    }
}
