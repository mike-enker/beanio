package org.beanio.internal.util;

import org.beanio.types.*;

/**
 * Default {@link Enum} type handler that uses {@link Enum#valueOf(Class, String)}
 * to parse a value and {@link Enum#name()} to format a value.
 * 
 * @author Kevin Seim
 * @since 2.0.1
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EnumTypeHandler<T extends Enum> implements TypeHandler<T> {

    private Class<T> type;
    
    /**
     * Constructs a new <tt>EnumTypeHandler</tt>.
     * @param type the Enum class
     */
    public EnumTypeHandler(Class<T> type) {
        this.type = type;
    }
    
    /*
     * (non-Javadoc)
     * @see org.beanio.types.TypeHandler#parse(java.lang.String)
     */
    public T parse(String text) throws TypeConversionException {
        if (text == null || "".equals(text)) {
            return null;
        }
        try {
            return (T) Enum.valueOf(type, text);
        }
        catch (IllegalArgumentException ex) {
            throw new TypeConversionException("Invalid " + getType().getSimpleName() + 
                " enum value '" + text + "'", ex);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.beanio.types.TypeHandler#format(java.lang.Object)
     */
    public String format(T value) {
        if (value == null) {
            return null;
        }
        return value.name();
    }

    /*
     * (non-Javadoc)
     * @see org.beanio.types.TypeHandler#getType()
     */
    public Class<T> getType() {
        return type;
    }
}
