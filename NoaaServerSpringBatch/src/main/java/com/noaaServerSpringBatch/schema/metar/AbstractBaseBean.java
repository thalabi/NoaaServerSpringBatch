/** 
 * $Id: AbstractBaseBean.java 340 2011-12-13 21:45:59Z  $ halabi
 */
package com.noaaServerSpringBatch.schema.metar;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBaseBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	transient Logger logger = LoggerFactory.getLogger(this.getClass());

	public AbstractBaseBean() {
        super();
    }

    /**
     * Compares two objects for equality.  If the objects are of the same type,
     * a field by field comparison is made using reflection.
     *
     * @param object - The object to be compared for equality.
     * @return boolean - True if the objects are equal
     */
    public boolean equals(final Object object) {

        return EqualsBuilder.reflectionEquals(this, object);
    }

    /**
     * Returns the hash code value of the business object.
     *
     * @return int - The hash code of the business object 
     */
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
	
}
