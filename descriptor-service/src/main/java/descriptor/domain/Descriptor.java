package descriptor.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "descriptor", indexes = {@Index(columnList = "NUMBER_VALUE", name = "idx_param_number_value"),
        @Index(columnList = "DOUBLE_VALUE", name = "idx_param_double_value"),
        @Index(columnList = "DATE_VALUE", name = "idx_param_date_value"),
        @Index(columnList = "STRING_VALUE", name = "idx_param_string_value")})
public class Descriptor implements Serializable {

    private static final long serialVersionUID = -2308547543297844947L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "document_type", nullable = false)
    private DocumentType documentType;

    @Column(name = "descriptor_key")
    @NotNull
    private String descriptorKey;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "descriptor_type", nullable = false)
    private DescriptorType descriptorType;

    @Column(name = "NUMBER_VALUE")
    private Long longValue;

    @Column(name = "DOUBLE_VALUE")
    private Double doubleValue;

    @Column(name = "DATE_VALUE")
    @Temporal(TemporalType.DATE)
    private Date dateValue;

    @Column(name = "STRING_VALUE")
    private String stringValue;

    private Long documentId;

    public Descriptor() {
    }

    public Descriptor(long id, String key, DocumentType documentType, DescriptorType descriptorType) {
        this.id = id;
        this.descriptorKey = key;
        this.documentType = documentType;
        this.descriptorType = descriptorType;
    }

    public Descriptor(String key, Object value, DocumentType documentType, DescriptorType descriptorType) {
        this.descriptorKey = key;
        this.documentType = documentType;
        this.descriptorType = descriptorType;
        setValue(value);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriptorKey() {
        return descriptorKey;
    }

    public void setDescriptorKey(String descriptorKey) {
        this.descriptorKey = descriptorKey;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public DescriptorType getDescriptorType() {
        return descriptorType;
    }

    public void setDescriptorType(DescriptorType descriptorType) {
        this.descriptorType = descriptorType;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Descriptor other = (Descriptor) obj;
        return !(!Objects.equals(this.descriptorKey, other.descriptorKey) || !Objects
                .equals(this.getValue(), other.getValue()));
    }

    @Override
    public String toString() {
        return "Descriptor{" + "id=" + id + ", documentType=" + documentType.getName() + ", descriptorKey="
                + descriptorKey + ", descriptorType=" + descriptorType + ", longValue=" + longValue + ", doubleValue="
                + doubleValue + ", dateValue=" + dateValue + ", stringValue=" + stringValue + '}';
    }

    public Object getValue() {
        Class paramClass = descriptorType.getParamClass();
        if (Integer.class.equals(paramClass)) {
            return longValue != null ? longValue.intValue() : null;
        } else if (Long.class.equals(paramClass)) {
            return longValue;
        } else if (Double.class.equals(paramClass)) {
            return doubleValue;
        } else if (String.class.equals(paramClass)) {
            return stringValue;
        } else if (Date.class.equals(paramClass)) {
            return dateValue;
        }
        return null;
    }

    public void setValue(Object value) {
        Class paramClass = descriptorType.getParamClass();
        System.out.println("paramClass " + paramClass);
        try {
            if (value == null) {
                longValue = null;
                doubleValue = null;
                stringValue = null;
                dateValue = null;
            } else {
                if (Integer.class.equals(paramClass)) {
                    if (value instanceof String) {
                        Integer valueInt = Integer.parseInt(value.toString());
                        longValue = (valueInt).longValue();
                    } else {
                        longValue = ((Integer) value).longValue();
                    }
                    doubleValue = longValue.doubleValue();
                } else if (Long.class.equals(paramClass)) {
                    if (value instanceof String) {
                        longValue = Long.parseLong(value.toString());
                    } else {
                        longValue = ((Long) value);
                    }
                    doubleValue = longValue.doubleValue();
                } else if (Double.class.equals(paramClass)) {
                    if (value instanceof String) {
                        doubleValue = Double.parseDouble(value.toString());
                    } else {
                        doubleValue = ((Double) value);
                    }
                    longValue = doubleValue.longValue();
                } else if (String.class.equals(paramClass)) {
                    stringValue = (String) value;
                } else if (Date.class.equals(paramClass)) {
                    try {
                        dateValue = new SimpleDateFormat("dd.MM.yyyy").parse(value.toString());
                    } catch (ParseException ex) {
                        dateValue = (Date) value;
                    }
                }
            }
        } catch (Exception ex) {
            longValue = null;
            doubleValue = null;
            stringValue = null;
            dateValue = null;
        }
    }
}
