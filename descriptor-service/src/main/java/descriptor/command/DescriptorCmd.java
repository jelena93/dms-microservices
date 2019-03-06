package descriptor.command;

public class DescriptorCmd {
    private Long documentTypeId;
    private String descriptorKey;
    private String descriptorValue;

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public String getDescriptorKey() {
        return descriptorKey;
    }

    public String getDescriptorValue() {
        return descriptorValue;
    }

    public DescriptorCmd(Long documentTypeId, String descriptorKey, String descriptorValue) {
        this.documentTypeId = documentTypeId;
        this.descriptorKey = descriptorKey;
        this.descriptorValue = descriptorValue;
    }

    @Override
    public String toString() {
        return "DescriptorCmd{" +
                "documentTypeId=" + documentTypeId +
                ", descriptorKey='" + descriptorKey + '\'' +
                ", descriptorValue='" + descriptorValue + '\'' +
                '}';
    }
}
