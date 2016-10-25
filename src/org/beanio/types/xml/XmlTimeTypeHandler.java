/*
 * Copyright 2011 Kevin Seim
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.beanio.types.xml;

import java.util.*;

import javax.xml.datatype.*;
import javax.xml.namespace.QName;

/**
 * A <tt>java.util.Date</tt> type handler implementation for parsing times based on
 * the W3C XML Schema <a href="http://www.w3.org/TR/xmlschema-2/#time">time</a> datatype
 * specification.
 * 
 * @author Kevin Seim
 * @since 1.1
 */
public class XmlTimeTypeHandler extends AbstractXmlDateTypeHandler {

    private boolean outputMilliseconds = false;
    
    /**
     * Constructs a new <tt>XmlTimeTypeHandler</tt>.
     */
    public XmlTimeTypeHandler() { }
    
    @Override
    public String format(Date date) {
        if (date == null) {
            return null;
        }

        if (pattern != null) {
            return super.formatDate(date);
        }
        
        Calendar cal = newCalendar();
        cal.setTime(date);
        
        int ms = DatatypeConstants.FIELD_UNDEFINED;
        if (outputMilliseconds) {
            ms = cal.get(Calendar.MILLISECOND);
        }
        
        XMLGregorianCalendar xcal = dataTypeFactory.newXMLGregorianCalendarTime(
            cal.get(Calendar.HOUR_OF_DAY), 
            cal.get(Calendar.MINUTE), 
            cal.get(Calendar.SECOND),
            ms,
            getTimeZoneOffset(date));
        
        return xcal.toXMLFormat();
    }

    @Override
    protected QName getDatatypeQName() {
        return DatatypeConstants.TIME;
    }

    /**
     * Returns whether milliseconds are included when formatting the time.
     * @return <tt>true</tt> if milliseconds are included when formatting the time
     */
    public boolean isOutputMilliseconds() {
        return outputMilliseconds;
    }

    /**
     * Sets whether milliseconds are included when formatting the time.  Set
     * to <tt>false</tt> by default.
     * @param b set to <tt>true</tt> to include milliseconds when formatting the time
     */
    public void setOutputMilliseconds(boolean b) {
        this.outputMilliseconds = b;
    }
}
