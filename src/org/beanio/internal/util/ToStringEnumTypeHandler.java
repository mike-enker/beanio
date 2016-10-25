package org.beanio.internal.util;

import org.beanio.types.TypeConversionException;
import org.beanio.types.TypeHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * An {@link Enum} type handler that uses {@link Enum#toString()} 
 * to parse and format Enum values.
 * 
 * @author Kevin Seim
 * @since 2.0.1
 */
@SuppressWarnings({"rawtypes"})
public class ToStringEnumTypeHandler<T extends Enum> implements TypeHandler<T> {

    private Class<T> type;
    private Map<String,T> map;
    
    /**
     * Constructs a new <tt>ToStringEnumTypeHandler</tt>.
     * @param type the Enum class
     */
    public ToStringEnumTypeHandler(Class<T> type) {
        this.type = type;
        
        map = new HashMap<>();
        
        T[] values = type.getEnumConstants();
        for (T value : values) {
            map.put(value.toString(), value);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.beanio.types.TypeHandler#parse(java.lang.String)
     */
    public T parse(String text) throws TypeConversionException {
        if (text == null || "".equals(text)) {
            return null;
        }
        
        T value = map.get(text);
        if (value == null) {
            throw new TypeConversionException("Invalid " + getType().getSimpleName() + 
                " enum value '" + text + "'");                
        }
        return value;
    }

    /*
     * (non-Javadoc)
     * @see org.beanio.types.TypeHandler#format(java.lang.Object)
     */
    public String format(T value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    /*
     * (non-Javadoc)
     * @see org.beanio.types.TypeHandler#getType()
     */
    public Class<T> getType() {
        return type;
    }
}
